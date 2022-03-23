package uk.gov.beis.els.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
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
  public void testProcessPdfResponse() throws IOException {
    ProcessedEnergyLabelDocument doc = new ProcessedEnergyLabelDocument(
        Jsoup.parse("<html><head><title>Dishwashers - name - model</title></head><body>foo</body></html>"), ProductMetadata.DISHWASHERS, "x", "name - model");

    ResponseEntity responseEntity = documentRendererService.processPdfResponse(doc);

    PDDocument generatedPdf = PDDocument.load(((ByteArrayResource)responseEntity.getBody()).getByteArray());
    assertThat(generatedPdf.getNumberOfPages()).isEqualTo(1);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - name - model.pdf");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.ENERGY_LABEL, "name - model", ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessPdfResponse_MultiplePages() throws IOException {
    List<ProcessedEnergyLabelDocument> docs = new ArrayList<>();

    docs.add(new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Fiche 1</title>"), ProductMetadata.DISHWASHERS, "x", "name - model"));
    docs.add(new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Fiche 2</title>"), ProductMetadata.OVENS_ELECTRIC, "x", "name - model"));

    ResponseEntity responseEntity = documentRendererService.processPdfResponse(docs);

    PDDocument generatedPdf = PDDocument.load(((ByteArrayResource)responseEntity.getBody()).getByteArray());
    assertThat(generatedPdf.getNumberOfPages()).isEqualTo(2);

    // The metadata from the first document should be used.
    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Fiche 1.pdf");
    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.ENERGY_LABEL, "name - model", ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessPdfResponse_SanitiseFilename() {
    ProcessedEnergyLabelDocument doc = new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Dishwashers - b/a\\d?f%i*l:e|n\"a&lt;m&gt;e - model</title>"), ProductMetadata.DISHWASHERS, "x", "b/a\\d?f%i*l:e|n\"a<m>e - model");

    ResponseEntity responseEntity = documentRendererService.processPdfResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - b_a_d_f_i_l_e_n_a_m_e - model.pdf");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.ENERGY_LABEL, "b/a\\d?f%i*l:e|n\"a<m>e - model", ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessImageResponse_Png() {
    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "PNG", "y");

    ResponseEntity responseEntity = documentRendererService.processImageResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.png");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.INTERNET_LABEL, "y", ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

  @Test
  public void testProcessImageResponse_Jpeg() {
    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "JPEG", "y");

    ResponseEntity responseEntity = documentRendererService.processImageResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.jpg");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent("x", GoogleAnalyticsEventCategory.INTERNET_LABEL, "y", ProductMetadata.DISHWASHERS.getAnalyticsLabel());
  }

}
