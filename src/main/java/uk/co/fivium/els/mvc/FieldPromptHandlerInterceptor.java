package uk.co.fivium.els.mvc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.FieldPrompt;

@Service
public class FieldPromptHandlerInterceptor implements HandlerInterceptor {

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
      }
    }
  }

  private Map<String, String> getFieldPromptMapping(Object form) {
    Map<String, String> fieldPrompts = new HashMap<>();
    Class<?> formClass = form.getClass();

    for (Field field : formClass.getDeclaredFields()) {
      String name = field.getName();
      FieldPrompt fieldPromptAnnotation = field.getAnnotation(FieldPrompt.class);
      if(fieldPromptAnnotation != null) {
        String prompt = fieldPromptAnnotation.value();
        fieldPrompts.put(FORM_MODEL_ATTRIBUTE_NAME + "." + name, prompt);
      }
    }

    return fieldPrompts;
  }

}
