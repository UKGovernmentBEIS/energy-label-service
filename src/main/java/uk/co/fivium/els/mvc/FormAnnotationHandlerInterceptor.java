package uk.co.fivium.els.mvc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.co.fivium.els.model.LabelMode;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.InternetLabelModeField;
import uk.co.fivium.els.model.meta.StaticProductText;
import uk.co.fivium.els.util.StreamUtils;

@Service
public class FormAnnotationHandlerInterceptor implements HandlerInterceptor {

  private static final String FORM_MODEL_ATTRIBUTE_NAME = "form";

  @Override
  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView) {
    if (modelAndView != null) {
      Object form = modelAndView.getModelMap().get(FORM_MODEL_ATTRIBUTE_NAME);
      if (form != null) {
        modelAndView.addObject("fieldPromptMapping",  getFieldPromptMapping(form));
        getStaticProductText(form).ifPresent(t -> modelAndView.addObject("staticProductText", t));
        modelAndView.addObject("fieldWidthMapping", getFieldWidthMapping(form));
        addInternetLabelModelObjects(request, modelAndView);

        modelAndView.addObject("hiddenFields", getHiddenFields(form, modelAndView));

      }
    }
  }

  private void addInternetLabelModelObjects(HttpServletRequest request, ModelAndView modelAndView) {

    if (StringUtils.isNotBlank(request.getQueryString()) && request.getQueryString().contains("mode=INTERNET")) {
      modelAndView.addObject("labelMode", LabelMode.INTERNET);
      modelAndView.addObject("modeQueryParam", "?mode=INTERNET");
    } else {
      modelAndView.addObject("labelMode", LabelMode.ENERGY);
    }


    modelAndView.addObject("internetLabelFormatOptions",
        Arrays.stream(InternetLabelFormat.values())
          .collect(StreamUtils.toLinkedHashMap(Enum::name, Enum::name))
    );

    modelAndView.addObject("internetLabelOrientationOptions",
        Arrays.stream(InternetLabelOrientation.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, InternetLabelOrientation::getDisplayName))
    );

  }

  private List<String> getHiddenFields(Object form, ModelAndView modelAndView) {

    List<String> hiddenFields = new ArrayList<>();
    Class<?> formClass = form.getClass();

    LabelMode labelMode = (LabelMode) modelAndView.getModel().get("labelMode");
    if (labelMode == LabelMode.ENERGY) {
      // In energy mode, hidden fields are those annotated with InternetLabelModeField
      ReflectionUtils.doWithFields(formClass, (field -> {
        String name = field.getName();
        InternetLabelModeField internetLabelAnnotation = field.getAnnotation(InternetLabelModeField.class);
        if(internetLabelAnnotation != null) {
          hiddenFields.add(FORM_MODEL_ATTRIBUTE_NAME + "." + name);
        }
      }));

    } else if (labelMode == LabelMode.INTERNET) {
      // In internet mode, hidden fields are those NOT annotated with InternetLabelModeField or DualModeField
      ReflectionUtils.doWithFields(formClass, (field -> {
        String name = field.getName();
        InternetLabelModeField internetLabelAnnotation = field.getAnnotation(InternetLabelModeField.class);
        DualModeField dualModeField = field.getAnnotation(DualModeField.class);
        if(internetLabelAnnotation == null && dualModeField == null) {
          hiddenFields.add(FORM_MODEL_ATTRIBUTE_NAME + "." + name);
        }
      }));
    }
    return hiddenFields;
  }

  private Map<String, FieldPrompt> getFieldPromptMapping(Object form) {
    Map<String, FieldPrompt> fieldPrompts = new HashMap<>();
    Class<?> formClass = form.getClass();

    ReflectionUtils.doWithFields(formClass, (field -> {
      String name = field.getName();
      FieldPrompt fieldPromptAnnotation = field.getAnnotation(FieldPrompt.class);
      if(fieldPromptAnnotation != null) {
        fieldPrompts.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, fieldPromptAnnotation);
      }
    }));

    return fieldPrompts;
  }

  private Optional<String> getStaticProductText(Object form) {
    Class<?> formClass = form.getClass();
    StaticProductText staticProductText = formClass.getAnnotation(StaticProductText.class);
    if (staticProductText == null) {
      return Optional.empty();
    } else {
      return Optional.of(staticProductText.value());
    }
  }

  /**
   * Calculate the max allowed length of the field based on validation annotations (@Digits, @Length and specific @Pattern's).
   * This is then queried in the input field template to set the correct 'govuk-input--width-x' class
   *
   * Note: if a field has multiple supported annotations the last one 'wins'
   * @param form The form object to scan for annotations
   * @return A map of field name to field width
   */
  private Map<String, String> getFieldWidthMapping(Object form) {
    Map<String, String> fieldWidths = new HashMap<>();
    Class<?> formClass = form.getClass();

    ReflectionUtils.doWithFields(formClass, (field -> {
      processFieldWidthDigitsAnnotations(field, fieldWidths);
      processFieldWidthLengthAnnotations(field, fieldWidths);
      processFieldWidthPatternAnnotations(field, fieldWidths);
    }));

    return fieldWidths;
  }

  private void processFieldWidthDigitsAnnotations(Field field, Map<String, String> fieldWidths) {
    String name = field.getName();
    Digits digitsAnnotation = field.getAnnotation(Digits.class);
    if(digitsAnnotation != null) {
      int integers = digitsAnnotation.integer();
      int decimals = digitsAnnotation.fraction();

      int fieldWidth;

      if (decimals > 0) {
        fieldWidth = integers + 1 + decimals; // +1 for decimal place
      } else {
        fieldWidth = integers;
      }
      fieldWidths.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, String.valueOf(fieldWidth));
    }

  }

  private void processFieldWidthLengthAnnotations(Field field, Map<String, String> fieldWidths) {
    String name = field.getName();
    Length lengthAnnotation = field.getAnnotation(Length.class);
    if(lengthAnnotation != null) {
      int length = lengthAnnotation.max();
      // if not set defaults to int max value
      if (length != Integer.MAX_VALUE) {
        fieldWidths.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, String.valueOf(length));
      }
    }
  }

  private void processFieldWidthPatternAnnotations(Field field, Map<String, String> fieldWidths) {
    String name = field.getName();
    Pattern patternAnnotation = field.getAnnotation(Pattern.class);
    if(patternAnnotation != null) {
      String pattern = patternAnnotation.regexp();
      // TODO this is the only pattern used currently. To be replaced by a nullable variant of the Digits annotation.
      if (pattern.equals("[0-9]{0,2}")) {
        fieldWidths.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, "2");
      }
    }
  }

}
