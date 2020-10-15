package uk.gov.beis.els.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.model.GoogleAnalyticsEventAction;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.renderer.JpegRenderer;
import uk.gov.beis.els.renderer.PdfRenderer;
import uk.gov.beis.els.renderer.PngRenderer;
import uk.gov.beis.els.renderer.Renderer;

@Service
public class DocumentRendererService {

  private final PdfRenderer pdfRenderer;
  private final PngRenderer pngRenderer;
  private final JpegRenderer jpegRenderer;
  private final AnalyticsService analyticsService;

  @Autowired
  public DocumentRendererService(PdfRenderer pdfRenderer, PngRenderer pngRenderer,
                                 JpegRenderer jpegRenderer, AnalyticsService analyticsService) {
    this.pdfRenderer = pdfRenderer;
    this.pngRenderer = pngRenderer;
    this.jpegRenderer = jpegRenderer;
    this.analyticsService = analyticsService;
  }

  public ResponseEntity processPdfResponse(ProcessedEnergyLabelDocument processedDocument) {

    Resource pdf = pdfRenderer.render(processedDocument.getDocument());

    analyticsService.sendGoogleAnalyticsEvent(processedDocument.getClientAnalyticsToken(),
        GoogleAnalyticsEventCategory.ENERGY_LABEL,
        GoogleAnalyticsEventAction.DOWNLOAD,
        processedDocument.getProductMetadata().getAnalyticsLabel());

    return serveResource(pdf, generatePdfFilename(processedDocument));

  }

  public ResponseEntity processImageResponse(ProcessedInternetLabelDocument processedDocument) {

    Renderer renderer;
    if (processedDocument.getInternetLabelFormat() == InternetLabelFormat.PNG) {
      renderer = pngRenderer;
    } else {
      renderer = jpegRenderer;
    }

    analyticsService.sendGoogleAnalyticsEvent(processedDocument.getClientAnalyticsToken(),
        GoogleAnalyticsEventCategory.INTERNET_LABEL,
        GoogleAnalyticsEventAction.DOWNLOAD,
        processedDocument.getProductMetadata().getAnalyticsLabel());


    return serveResource(renderer.render(processedDocument.getDocument()), generateImageFilename(processedDocument));
  }

  private String generatePdfFilename(ProcessedEnergyLabelDocument processedDocument) {
    String filename = processedDocument.getDocument().title()+".pdf";
    return sanitiseFilename(filename);
  }

  private String generateImageFilename(ProcessedInternetLabelDocument processedDocument) {
    String filename = String.format(processedDocument.getProductMetadata().getProductFileName() + " internet arrow %s%s", processedDocument.getRatingClass().getDisplayValue(), processedDocument.getInternetLabelFormat().getFileExtension());
    return sanitiseFilename(filename);
  }

  private String sanitiseFilename(String filename) {
    // Replace '/', '\', '?', '%', '*', ':', '|', '"', '<', '>'
    String sanitisedFilename = filename.replaceAll("[/\\\\?%*:|\"<>]", "_");

    // Windows filename length limit
    if (sanitisedFilename.length() > 255) {
      return sanitisedFilename.substring(0, 255);
    } else {
      return sanitisedFilename;
    }
  }

  private ResponseEntity serveResource(Resource resource, String fileName) {
    try {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .contentLength(resource.contentLength())
          .header(
              HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
          .body(resource);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Error serving file '%s'", fileName), e);
    }
  }

}
