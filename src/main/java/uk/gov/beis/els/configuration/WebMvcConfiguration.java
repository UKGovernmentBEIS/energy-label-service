package uk.gov.beis.els.configuration;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.beis.els.mvc.FormAnnotationHandlerInterceptor;
import uk.gov.beis.els.mvc.RateLimitHandlerInterceptor;
import uk.gov.beis.els.mvc.ResponseBufferSizeHandlerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);

  private final RateLimitHandlerInterceptor rateLimitHandlerInterceptor;

  @Autowired
  public WebMvcConfiguration(RateLimitHandlerInterceptor rateLimitHandlerInterceptor) {
    this.rateLimitHandlerInterceptor = rateLimitHandlerInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new FormAnnotationHandlerInterceptor())
        .excludePathPatterns("/assets/**");
    registry.addInterceptor(new ResponseBufferSizeHandlerInterceptor())
        .excludePathPatterns("/assets/**");
    registry.addInterceptor(rateLimitHandlerInterceptor)
        .addPathPatterns("/api/v1/**");
  }

  @EventListener(ApplicationReadyEvent.class)
  public void registerFonts() {
    try {
      boolean calibriLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/calibri.ttf").getInputStream()));

      LOGGER.info("Calibri loaded {}", calibriLoaded);

      boolean calibriBoldLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/calibri-bold.ttf").getInputStream()));

      LOGGER.info("Calibri-bold loaded {}", calibriBoldLoaded);

      boolean verdanaLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
              Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/verdana.ttf").getInputStream()));

      LOGGER.info("Verdana loaded {}", verdanaLoaded);

      boolean verdanaBoldLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
              Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/verdana-bold.ttf").getInputStream()));

      LOGGER.info("Verdana-bold loaded {}", verdanaBoldLoaded);

      LOGGER.info("Available system fonts are:");
      Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).forEach(f ->
          LOGGER.info("Font: {}", f)
      );

    } catch (Exception e) {
      LOGGER.error("Failed to load fonts during app start", e);
    }
  }

}
