package uk.gov.beis.els.api.openapi;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.annotations.ApiModelProperty;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.Digits;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * Sets OpenAPI documentation fields based on custom annotations.
 */
@Component
@Order(SwaggerPluginSupport.OAS_PLUGIN_ORDER)
public class CustomApiModelPlugin implements ModelPropertyBuilderPlugin {

  @Override
  public void apply(ModelPropertyContext context) {
    Optional<AnnotatedElement> element = context.getAnnotatedElement();

    if(!element.isPresent()) {
      // Try to resolve as bean
      Optional<BeanPropertyDefinition> beanProperty = context.getBeanPropertyDefinition();
      element = beanProperty.map(bp -> bp.getField().getAnnotated());
    }

    element.ifPresent(e -> {
      processFieldPrompts(e, context);
      processLegislationCategoryValues(e, context);
    });
  }

  /**
   * Sets the description field based on the FieldPrompt and optional Digits annotations, if it has not been overridden
   * by a description defined by the ApiModelProperty annotation.
   * @param element the annotated element
   * @param context the context
   */
  private void processFieldPrompts(AnnotatedElement element, ModelPropertyContext context) {
    FieldPrompt fieldPrompt = element.getAnnotation(FieldPrompt.class);
    if(fieldPrompt != null) {
      ApiModelProperty modelProperty = element.getAnnotation(ApiModelProperty.class);
      if(modelProperty == null || modelProperty.value().isEmpty()) {
        // Only set description if it has not been overridden by an ApiModelProperty description
        context.getSpecificationBuilder()
            .description(fieldPrompt.value() + stringifyDigitsConstraint(element.getAnnotation(Digits.class)));
      }
    }
  }

  /**
   * Returns a string representation of the Digits annotation. The OpenAPI spec does not allow for the number of digits/decimal places
   * to be specified explicitly as a constraint, so we just include it in the field description.
   * @param digits the Digits annotation param. If null an empty string is returned.
   * @return The digits constraint expressed as a string.
   */
  private String stringifyDigitsConstraint(Digits digits) {
    if (digits == null) {
      return "";
    }
    int ints = digits.integer();
    int fractions = digits.fraction();
    if (ints > 0 && fractions > 0) {
      return String.format(". This may be up to %d digit(s) long with an optional %d decimal places.", ints, fractions);
    } else if (ints > 0) {
      return String.format(". This may be up to %d digit(s) long.", ints);
    } else {
      return "";
    }
  }

  /**
   * Sets the allowedValues list from a LegislationCategory field accessed via reflection.
   * @param element the annotated element
   * @param context the context
   */
  private void processLegislationCategoryValues(AnnotatedElement element, ModelPropertyContext context) {
    ApiValuesFromLegislationCategory apiModelProperty =  element.getAnnotation(ApiValuesFromLegislationCategory.class);
    if(apiModelProperty != null) {
      try {
        Field field = apiModelProperty.serviceClass().getField(apiModelProperty.legislationCategoryFieldName());
        LegislationCategory legislationCategory = (LegislationCategory) field.get(null); // Null as we're accessing a static field
        RatingClassRange range = legislationCategory.getPrimaryRatingRange();

        List<String> allowedValues = range.getApplicableRatings().stream()
          .map(RatingClass::name) // TODO may be nicer to accept the display value i.e 'A++' rather than 'APP'
          .collect(Collectors.toList());

        context
          .getSpecificationBuilder()
          .enumerationFacet((f) -> f.allowedValues(new AllowableListValues(allowedValues, "java.lang.String")));
      } catch (Exception e) {
        throw new RuntimeException("Error processing ApiValuesFromLegislationCategory annotations", e);
      }
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return SwaggerPluginSupport.pluginDoesApply(delimiter);
  }

}
