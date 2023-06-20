package uk.gov.beis.els.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.model.EnergyLabelFormat;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.model.GoogleAnalyticsEventParams;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.renderer.JpegRenderer;
import uk.gov.beis.els.renderer.PdfRenderer;
import uk.gov.beis.els.renderer.PngRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class DocumentRendererServiceTest {

  private DocumentRendererService documentRendererService;

  @Mock
  private AnalyticsService analyticsService;

  @Captor
  private ArgumentCaptor<GoogleAnalyticsEventParams> analyticsCaptor;

  @Before
  public void setUp() {
    documentRendererService = new DocumentRendererService(new PdfRenderer(), new PngRenderer(), new JpegRenderer(), analyticsService);
  }

  @Test
  public void testProcessResponse_Pdf() throws IOException {
    ProcessedEnergyLabelDocument doc = getPdfEnergyLabelDocument();

    ResponseEntity responseEntity = documentRendererService.processResponse(doc);

    PDDocument generatedPdf = PDDocument.load(((ByteArrayResource)responseEntity.getBody()).getByteArray());
    assertThat(generatedPdf.getNumberOfPages()).isEqualTo(1);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - name - model.pdf");

    verify(analyticsService, times(1))
        .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessResponse_Pdf_MultiplePages() throws IOException {
    List<ProcessedEnergyLabelDocument> docs = new ArrayList<>();

    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("foo", "bar");

    docs.add(new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Fiche 1</title>"), ProductMetadata.DISHWASHERS, "x", analyticsParams, EnergyLabelFormat.PDF));
    docs.add(new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Fiche 2</title>"), ProductMetadata.OVENS_ELECTRIC, "x", analyticsParams, EnergyLabelFormat.PDF));

    ResponseEntity responseEntity = documentRendererService.processResponse(docs);

    PDDocument generatedPdf = PDDocument.load(((ByteArrayResource)responseEntity.getBody()).getByteArray());
    assertThat(generatedPdf.getNumberOfPages()).isEqualTo(2);

    // The metadata from the first document should be used.
    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Fiche 1.pdf");
    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(analyticsParams);
  }

  @Test
  public void testProcessResponse_Png() throws IOException {
    ProcessedEnergyLabelDocument doc = getPngEnergyLabelDocument();
    ResponseEntity responseEntity = documentRendererService.processResponse(doc);

    ByteArrayResource response = (ByteArrayResource)responseEntity.getBody();
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.getByteArray()));

    assertThat(image.getType()).isEqualTo(BufferedImage.TYPE_4BYTE_ABGR);
    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - name - model.png");

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessResponse_Jpeg() throws IOException {
    ProcessedEnergyLabelDocument doc = getJpegEnergyLabelDocument();
    ResponseEntity responseEntity = documentRendererService.processResponse(doc);

    ByteArrayResource response = (ByteArrayResource)responseEntity.getBody();
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.getByteArray()));

    assertThat(image.getType()).isEqualTo(BufferedImage.TYPE_3BYTE_BGR);
    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - name - model.jpeg");

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessResponse_SanitiseFilename() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("foo", "bar");

    ProcessedEnergyLabelDocument doc = new ProcessedEnergyLabelDocument(
        Jsoup.parse("<title>Dishwashers - b/a\\d?f%i*l:e|n\"a&lt;m&gt;e - model</title>"), ProductMetadata.DISHWASHERS, "x", analyticsParams, EnergyLabelFormat.PDF);

    ResponseEntity responseEntity = documentRendererService.processResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers - b_a_d_f_i_l_e_n_a_m_e - model.pdf");

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessInternetLabelResponse_Png() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("foo", "bar");

    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" data-type=\"internet-label\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "PNG", analyticsParams);

    ResponseEntity responseEntity = documentRendererService.processInternetLabelResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.png");

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.INTERNET_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessInternetLabelResponse_Jpeg() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("foo", "bar");

    ProcessedInternetLabelDocument doc = new ProcessedInternetLabelDocument(
        Jsoup.parse("<svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" data-type=\"internet-label\"></svg>"), "AP", ProductMetadata.DISHWASHERS, "x", "JPEG", analyticsParams);

    ResponseEntity responseEntity = documentRendererService.processInternetLabelResponse(doc);

    assertThat(responseEntity.getHeaders().getContentDisposition().getFilename()).isEqualTo("Dishwashers internet arrow A+.jpg");

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.INTERNET_LABEL), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessApiResponse_Pdf() throws IOException {
    ProcessedEnergyLabelDocument doc = getPdfEnergyLabelDocument();

    ResponseEntity responseEntity = documentRendererService.processApiResponse(doc);

    PDDocument generatedPdf = PDDocument.load(((ByteArrayResource)responseEntity.getBody()).getByteArray());
    assertThat(generatedPdf.getNumberOfPages()).isEqualTo(1);

    assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PDF);

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL_API), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessApiResponse_Png() throws IOException {
    ProcessedEnergyLabelDocument doc = getPngEnergyLabelDocument();
    ResponseEntity responseEntity = documentRendererService.processApiResponse(doc);

    ByteArrayResource response = (ByteArrayResource)responseEntity.getBody();
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.getByteArray()));

    assertThat(image.getType()).isEqualTo(BufferedImage.TYPE_4BYTE_ABGR);
    assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_PNG);

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL_API), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }

  @Test
  public void testProcessApiResponse_Jpeg() throws IOException {
    ProcessedEnergyLabelDocument doc = getJpegEnergyLabelDocument();
    ResponseEntity responseEntity = documentRendererService.processApiResponse(doc);

    ByteArrayResource response = (ByteArrayResource)responseEntity.getBody();
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.getByteArray()));

    assertThat(image.getType()).isEqualTo(BufferedImage.TYPE_3BYTE_BGR);
    assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_JPEG);

    verify(analyticsService, times(1))
            .sendGoogleAnalyticsEvent(eq("x"), eq(GoogleAnalyticsEventCategory.ENERGY_LABEL_API), analyticsCaptor.capture());

    assertThat(analyticsCaptor.getValue())
            .usingRecursiveComparison()
            .isEqualTo(doc.getAnalyticsEventParams());
  }


  private ProcessedEnergyLabelDocument getPdfEnergyLabelDocument() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("product_type", "product type");
    analyticsParams.addParam("product_subtype", "product subtype");
    analyticsParams.addParam("supplier", "supplier");
    analyticsParams.addParam("model", "model");
    analyticsParams.addParam("output_format", "PDF");

    return new ProcessedEnergyLabelDocument(
        Jsoup.parse("<html><head><title>Dishwashers - name - model</title></head><body>foo</body></html>"),
        ProductMetadata.DISHWASHERS,
        "x",
        analyticsParams,
        EnergyLabelFormat.PDF
    );
  }

  private ProcessedEnergyLabelDocument getPngEnergyLabelDocument() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("product_type", "product type");
    analyticsParams.addParam("product_subtype", "product subtype");
    analyticsParams.addParam("supplier", "supplier");
    analyticsParams.addParam("model", "model");
    analyticsParams.addParam("output_format", "PNG");

    return new ProcessedEnergyLabelDocument(
        Jsoup.parse("<html><head><title>Dishwashers - name - model</title></head><body data-width=\"50\" data-height=\"50\"><svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg></body></html>"),
        ProductMetadata.DISHWASHERS,
        "x",
        analyticsParams,
        EnergyLabelFormat.PNG
    );
  }

  private ProcessedEnergyLabelDocument getJpegEnergyLabelDocument() {
    GoogleAnalyticsEventParams analyticsParams = new GoogleAnalyticsEventParams();
    analyticsParams.addParam("product_type", "product type");
    analyticsParams.addParam("product_subtype", "product subtype");
    analyticsParams.addParam("supplier", "supplier");
    analyticsParams.addParam("model", "model");
    analyticsParams.addParam("output_format", "JPEG");

    return new ProcessedEnergyLabelDocument(
        Jsoup.parse("<html><head><title>Dishwashers - name - model</title></head><body data-width=\"50\" data-height=\"50\"><svg id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"></svg></body></html>"),
        ProductMetadata.DISHWASHERS,
        "x",
        analyticsParams,
        EnergyLabelFormat.JPEG
    );
  }

}
