package uk.gov.beis.els.configuration;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.beis.els.mvc.FormAnnotationHandlerInterceptor;
import uk.gov.beis.els.mvc.ResponseBufferSizeHandlerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new FormAnnotationHandlerInterceptor())
        .excludePathPatterns("/assets/**");
    registry.addInterceptor(new ResponseBufferSizeHandlerInterceptor())
        .excludePathPatterns("/assets/**");
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

      boolean arialLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/arial.ttf").getInputStream()));

      LOGGER.info("Arial loaded {}", arialLoaded);

      boolean arialBoldLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/arial-bold.ttf").getInputStream()));

      LOGGER.info("Arial-bold loaded {}", arialBoldLoaded);

      boolean myriadProLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/myriad-pro.ttf").getInputStream()));

      LOGGER.info("Myriad Pro loaded {}", myriadProLoaded);

      boolean myriadProBoldLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/myriad-pro-bold.ttf").getInputStream()));

      LOGGER.info("Myriad Pro Bold loaded {}", myriadProBoldLoaded);

      boolean myriadProSemiBoldLoaded = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
          Font.createFont(Font.TRUETYPE_FONT, new ClassPathResource("fonts/myriad-pro-semibold.ttf").getInputStream()));

      LOGGER.info("Myriad Pro Semi Bold loaded {}", myriadProSemiBoldLoaded);

      LOGGER.info("Available system fonts are:");
      Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).forEach(f ->
          LOGGER.info("Font: {}", f)
      );

    } catch (Exception e) {
      LOGGER.error("Failed to load fonts during app start", e);
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  public void tempDebug() {
    logVar("ANALYTICS_CONN_TIMEOUT_MS");
    logVar("ENABLE_GOOGLE_ANALYTICS");
    logVar("ENABLE_RATE_LIMITING");
    logVar("ENABLE_GOOGLE_ANALYTICS");
    logVar("HOME_PAGE_URL");
    logVar("RATE_LIMIT_CAPACITY");
    logVar("RATE_LIMIT_TIME_UNIT");
    logVar("RATE_LIMIT_TIME_VALUE");
    logVar("SHOW_START_PAGE");
  }

  private void logVar(String name) {
    LOGGER.info("var {} is {}", name, System.getenv(name));
  }

}
