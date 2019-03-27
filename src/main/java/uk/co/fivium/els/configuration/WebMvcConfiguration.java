package uk.co.fivium.els.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.co.fivium.els.mvc.FormAnnotationHandlerInterceptor;
import uk.co.fivium.els.mvc.ResponseBufferSizeHandlerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new FormAnnotationHandlerInterceptor())
        .excludePathPatterns("/assets/**");
    registry.addInterceptor(new ResponseBufferSizeHandlerInterceptor())
        .excludePathPatterns("/assets/**");
  }

}
