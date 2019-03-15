package uk.co.fivium.els.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

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
      }
    }
  }

  private Map<String, String> getFieldPromptMapping(Object form) {
    Map<String, String> fieldPrompts = new HashMap<>();
    Class<?> formClass = form.getClass();

    ReflectionUtils.doWithFields(formClass, (field -> {
      String name = field.getName();
      FieldPrompt fieldPromptAnnotation = field.getAnnotation(FieldPrompt.class);
      if(fieldPromptAnnotation != null) {
        String prompt = fieldPromptAnnotation.value();
        fieldPrompts.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, prompt);
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

}
