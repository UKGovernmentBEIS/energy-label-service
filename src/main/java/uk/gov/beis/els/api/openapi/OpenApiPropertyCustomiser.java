package uk.gov.beis.els.api.openapi;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.Digits;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange;
import uk.gov.beis.els.model.Displayable;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Component
@SuppressWarnings({"rawtypes", "unchecked"}) // the PropertyCustomizer interface works with raw types
public class OpenApiPropertyCustomiser implements PropertyCustomizer {

  @Override
  public Schema customize(Schema schema, AnnotatedType type) {
    List<Annotation> annotations = Arrays.asList(type.getCtxAnnotations());

    processFieldPrompts(annotations, schema);
    processLegislationCategoryValues(annotations, schema);
    processRatingClassRangeValues(annotations, schema);
    processEnumValues(annotations, schema);

    return schema;
  }

  private <T> Optional<T> getAnnotation(List<Annotation> annotations, Class<T> annotation) {
    return annotations.
        stream()
        .filter(annotation::isInstance)
        .map(annotation::cast)
        .findFirst();
  }

  /**
   * Sets the description field based on the FieldPrompt and optional Digits annotations, if it has not been overridden
   * by a description defined by the Schema annotation.
   * @param annotations the annotations on the element
   * @param schema the OpenAPI schema
   */
  private void processFieldPrompts(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, FieldPrompt.class)
        .ifPresent(fieldPrompt -> {
          Optional<io.swagger.v3.oas.annotations.media.Schema> schemaAnnotation = getAnnotation(annotations, io.swagger.v3.oas.annotations.media.Schema.class);
          if(!schemaAnnotation.isPresent() || schemaAnnotation.get().description().isEmpty()) {
            // Only set description if it has not been overridden by a Schema description
            schema.description(fieldPrompt.value() + stringifyDigitsConstraint(getAnnotation(annotations, Digits.class)));
          }
        });
  }

  /**
   * Returns a string representation of the Digits annotation. The OpenAPI spec does not allow for the number of digits/decimal places
   * to be specified explicitly as a constraint, so we just include it in the field description.
   * @param digits optional Digits annotation param. If empty or null, an empty string is returned.
   * @return The digits constraint expressed as a string.
   */
  private String stringifyDigitsConstraint(Optional<Digits> digits) {
    if (digits == null || !digits.isPresent()) {
      return "";
    }
    int ints = digits.get().integer();
    int fractions = digits.get().fraction();
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
   * @param annotations the annotations on the element
   * @param schema the OpenAPI schema
   */
  private void processLegislationCategoryValues(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, ApiValuesFromLegislationCategory.class)
        .ifPresent(apiValueAnnotation -> {
          try {
            Field field = apiValueAnnotation.serviceClass().getField(apiValueAnnotation.legislationCategoryFieldName());
            LegislationCategory legislationCategory = (LegislationCategory) field.get(null); // Null as we're accessing a static field
            RatingClassRange range;

            if (apiValueAnnotation.useSecondaryRange()) {
              range = legislationCategory.getSecondaryRatingRange();
            } else {
              range = legislationCategory.getPrimaryRatingRange();
            }

            List<String> allowedValues = range.getApplicableRatings().stream()
                .map(RatingClass::getDisplayValue)
                .collect(Collectors.toList());

            schema.setEnum(allowedValues);

          } catch (Exception e) {
            throw new RuntimeException("Error processing ApiValuesFromLegislationCategory annotations", e);
          }
        });
  }

  /**
   * Sets the allowedValues list from a RatingClassRange field accessed via reflection.
   * @param annotations the annotations on the element
   * @param schema the OpenAPI schema
   */
  private void processRatingClassRangeValues(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, ApiValuesFromRatingClassRange.class)
        .ifPresent(apiValueAnnotation -> {
          try {
            Field field = apiValueAnnotation.serviceClass().getField(apiValueAnnotation.ratingClassRangeFieldName());
            RatingClassRange range = (RatingClassRange) field.get(null); // Null as we're accessing a static field

            List<String> allowedValues = range.getApplicableRatings().stream()
                .map(RatingClass::getDisplayValue)
                .collect(Collectors.toList());

            schema.setEnum(allowedValues);

          } catch (Exception e) {
            throw new RuntimeException("Error processing RatingClassRange annotations", e);
          }
        });
  }

  private void processEnumValues(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, ApiValuesFromEnum.class)
        .ifPresent(apiValueAnnotation -> {
          try {
            Class<?> enumClass = apiValueAnnotation.value();
            List<Enum<?>> enumValues = (List<Enum<?>>) Arrays.asList(enumClass.getEnumConstants());

            List<String> allowedValues = enumValues.stream()
                    .map(anEnum -> {
                      if (anEnum instanceof Displayable) {
                        return ((Displayable) anEnum).getDisplayName();
                      } else {
                        return anEnum.name();
                      }
                    })
                    .collect(Collectors.toList());

            schema.setEnum(allowedValues);

          } catch (Exception e) {
            throw new RuntimeException("Error processing ApiValuesFromEnum annotations", e);
          }
        });
  }
}
