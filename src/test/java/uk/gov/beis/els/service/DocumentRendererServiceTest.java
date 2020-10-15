package uk.gov.beis.els.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.model.GoogleAnalyticsEventAction;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.renderer.JpegRenderer;
import uk.gov.beis.els.renderer.PdfRenderer;
import uk.gov.beis.els.renderer.PngRenderer;

@RunWith(MockitoJUnitRunner.class)
public class DocumentRendererServiceTest {

  private DocumentRendererService documentRendererService;

  @Mock
  private AnalyticsService analyticsService;

  @Before
  public void setUp() {
    documentRendererService = new DocumentRendererService(new PdfRenderer(), new PngRenderer(), new JpegRenderer(), analyticsService);
  }

  @Test
  public void testProcessPdfResponse() {
    ProcessedEnergyLabelDocument doc = new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Dishwashers - name - model</title>"), ProductMetadata.DISHWASHERS, "x");

    ResponseEntity responseEntity = documentRendererService.processPdfResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - name - model.pdf");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.ENERGY_LABEL, GoogleAnalyticsEventAction.DOWNLOAD, ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessPdfResponse_SanitiseFilename() {
    ProcessedEnergyLabelDocument doc = new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Dishwashers - b/a\\d?f%i*l:e|n\"a&lt;m&gt;e - model</title>"), ProductMetadata.DISHWASHERS, "x");

    ResponseEntity responseEntity = documentRendererService.processPdfResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - b_a_d_f_i_l_e_n_a_m_e - model.pdf");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.ENERGY_LABEL, GoogleAnalyticsEventAction.DOWNLOAD, ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessImageResponse_Png() {
    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "PNG");

    ResponseEntity responseEntity = documentRendererService.processImageResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.png");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.INTERNET_LABEL, GoogleAnalyticsEventAction.DOWNLOAD, ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessImageResponse_Jpeg() {
    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "JPEG");

    ResponseEntity responseEntity = documentRendererService.processImageResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.jpg");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.INTERNET_LABEL, GoogleAnalyticsEventAction.DOWNLOAD, ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

}
