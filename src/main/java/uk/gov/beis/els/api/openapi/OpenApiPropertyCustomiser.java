package uk.gov.beis.els.api.openapi;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.constraints.Digits;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList;
import uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange;
import uk.gov.beis.els.categories.common.LoadProfile;
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

    processFieldDescriptions(annotations, schema);
    processLegislationCategoryValues(annotations, schema);
    processRatingClassRangeValues(annotations, schema);
    processEnumValues(annotations, schema);
    processLoadProfileList(annotations, schema);
    processExamples(schema, type);

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
   * Sets the schema description field, based on the FieldPrompt or Schema annotation. If both are set, the Schema
   * annotation takes precedence. Digits validation rules are appended to the end of the description.
   * @param annotations the annotations on the element
   * @param schema the OpenAPI schema
   */
  private void processFieldDescriptions(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, FieldPrompt.class)
        .ifPresent(fieldPrompt -> {
          schema.description(combineText(fieldPrompt.value(), stringifyDigitsConstraint(getAnnotation(annotations, Digits.class))));
        });

    // Schema takes precedence if both are set
    getAnnotation(annotations, io.swagger.v3.oas.annotations.media.Schema.class)
        .ifPresent(schemaAnnotation -> {
          if (!schemaAnnotation.description().isEmpty()) {
            schema.description(combineText(schemaAnnotation.description(), stringifyDigitsConstraint(getAnnotation(annotations, Digits.class))));
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
      return String.format("This may be up to %d %s long with an optional %d decimal %s.",
          ints,
          pluralise(ints, "digit"),
          fractions,
          pluralise(fractions, "place")
      );
    } else if (ints > 0){
      return String.format("This may be up to %d %s long.", ints, pluralise(ints, "digit"));
    } else {
      return "";
    }
  }

  private String pluralise(int count, String item) {
    if (count == 1) {
      return item;
    } else {
      return item + "s";
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

  private void processLoadProfileList(List<Annotation> annotations, Schema schema) {
    getAnnotation(annotations, ApiValuesFromLoadProfileList.class)
        .ifPresent(apiValueAnnotation -> {
          try {
            Field field = apiValueAnnotation.serviceClass().getField(apiValueAnnotation.loadProfilesFieldName());
            List<LoadProfile> loadProfiles = (List<LoadProfile>) field.get(null);
            List<String> allowedValues = loadProfiles
                .stream()
                .map(LoadProfile::getDisplayName)
                .collect(Collectors.toList());

            schema.setEnum(allowedValues);
          } catch (Exception e) {
            throw new RuntimeException("Error processing ApiValuesFromLoadProfileList annotations", e);
          }
        });
  }

  private void processExamples(Schema schema, AnnotatedType type) {
    String propertyName = type.getPropertyName();
    if (schema.getType().equals("integer")) { // process integers
      if (propertyName.equals("productPriceHeightPx")) {
        schema.setExample("100"); // set pixels to something the user can actually see (i.e. bigger than 1 pixel)
      } else {
        schema.setExample("1");
      }
    } else if (schema.getType().equals("number")) { // process numbers (i.e. those that allow decimal places
      schema.setExample("1.1");
    } else if (propertyName.equals("qrCodeUrl")) {
      schema.setExample("https://example.com"); // default example for QR code website fields
    } else if (schema.getType().equals("string")) {
      schema.setExample("example"); // process all other strings
    } else if (schema.getType().equals("boolean")) {
      schema.setExample("true");
    }

    // process enum objects (these have type "string" so this will overwrite the default string value above)
    if (schema.getEnum() != null) {
      schema.setExample(schema.getEnum().get(0)); // set the example to the first enum value in the list
    }
  }

  private String combineText(String text1, String text2) {
    if (text2.isEmpty()) {
      return text1 + (text1.endsWith(".") || text1.endsWith("?") ? "" : ".");
    }
    return text1 + (text1.endsWith(".") || text1.endsWith("?") ? " " : ". ") + text2;
  }
}
