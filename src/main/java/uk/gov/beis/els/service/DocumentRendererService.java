package uk.gov.beis.els.service;

import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.renderer.JpegRenderer;
import uk.gov.beis.els.renderer.PdfRenderer;
import uk.gov.beis.els.renderer.PngRenderer;
import uk.gov.beis.els.renderer.Renderer;

@Service
public class DocumentRendererService {

  private enum ResponseType {
    ATTACHMENT,
    DIRECT
  }

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
    return processPdfResponse(processedDocument, GoogleAnalyticsEventCategory.ENERGY_LABEL);
  }

  public ResponseEntity processPdfResponse(ProcessedEnergyLabelDocument processedDocument, GoogleAnalyticsEventCategory analyticsCategory) {
    return processPdf(processedDocument, ResponseType.ATTACHMENT, analyticsCategory);
  }

  public ResponseEntity processPdfResponse(List<ProcessedEnergyLabelDocument> processedDocuments) {
    return processPdfResponse(processedDocuments, GoogleAnalyticsEventCategory.ENERGY_LABEL);
  }

  public ResponseEntity processPdfResponse(List<ProcessedEnergyLabelDocument> processedDocuments, GoogleAnalyticsEventCategory analyticsCategory) {
    return processPdf(processedDocuments, ResponseType.ATTACHMENT, analyticsCategory);
  }

  public ResponseEntity processPdfApiResponse(ProcessedEnergyLabelDocument processedDocument) {
    return processPdfApiResponse(processedDocument, GoogleAnalyticsEventCategory.ENERGY_LABEL_API);
  }

  public ResponseEntity processPdfApiResponse(ProcessedEnergyLabelDocument processedDocument, GoogleAnalyticsEventCategory analyticsCategory) {
    return processPdf(processedDocument, ResponseType.DIRECT, analyticsCategory);
  }

  public ResponseEntity processPdfApiResponse(List<ProcessedEnergyLabelDocument> processedDocuments) {
    return processPdfApiResponse(processedDocuments, GoogleAnalyticsEventCategory.ENERGY_LABEL_API);
  }

  public ResponseEntity processPdfApiResponse(List<ProcessedEnergyLabelDocument> processedDocuments, GoogleAnalyticsEventCategory analyticsCategory) {
    return processPdf(processedDocuments, ResponseType.DIRECT, analyticsCategory);
  }

  public ResponseEntity processImageResponse(ProcessedInternetLabelDocument processedDocument) {
    return processImage(processedDocument, ResponseType.ATTACHMENT, GoogleAnalyticsEventCategory.INTERNET_LABEL);
  }

  public ResponseEntity processImageApiResponse(ProcessedInternetLabelDocument processedDocument) {
    return processImage(processedDocument, ResponseType.DIRECT, GoogleAnalyticsEventCategory.INTERNET_LABEL_API);
  }

  private ResponseEntity processPdf(ProcessedEnergyLabelDocument processedDocument, ResponseType responseType, GoogleAnalyticsEventCategory eventCategory) {
    Resource pdf = pdfRenderer.render(processedDocument.getDocument());

    analyticsService.sendGoogleAnalyticsEvent(processedDocument.getClientAnalyticsToken(),
        eventCategory,
        processedDocument.getAnalyticsEventAction(),
        processedDocument.getProductMetadata().getAnalyticsLabel());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(pdf, generatePdfFilename(processedDocument));
    } else {
      return serveWithContentType(pdf, pdfRenderer.getTargetContentType());
    }
  }

  private ResponseEntity processPdf(List<ProcessedEnergyLabelDocument> processedDocuments, ResponseType responseType, GoogleAnalyticsEventCategory eventCategory) {
    List<Document> htmlDocuments = processedDocuments.stream()
        .map(ProcessedEnergyLabelDocument::getDocument)
        .collect(Collectors.toList());

    Resource pdf = pdfRenderer.render(htmlDocuments);

    // Use the first document for metadata
    ProcessedEnergyLabelDocument primaryDocument = processedDocuments.get(0);
    analyticsService.sendGoogleAnalyticsEvent(primaryDocument.getClientAnalyticsToken(),
        eventCategory,
        primaryDocument.getAnalyticsEventAction(),
        primaryDocument.getProductMetadata().getAnalyticsLabel());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(pdf, generatePdfFilename(primaryDocument));
    } else {
      return serveWithContentType(pdf, pdfRenderer.getTargetContentType());
    }
  }

  private ResponseEntity processImage(ProcessedInternetLabelDocument processedDocument, ResponseType responseType, GoogleAnalyticsEventCategory eventCategory) {
    Renderer renderer;
    if (processedDocument.getInternetLabelFormat() == InternetLabelFormat.PNG) {
      renderer = pngRenderer;
    } else {
      renderer = jpegRenderer;
    }

    analyticsService.sendGoogleAnalyticsEvent(processedDocument.getClientAnalyticsToken(),
        eventCategory,
        processedDocument.getAnalyticsEventAction(),
        processedDocument.getProductMetadata().getAnalyticsLabel());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(renderer.render(processedDocument.getDocument()), generateImageFilename(processedDocument));
    } else {
      return serveWithContentType(renderer.render(processedDocument.getDocument()), renderer.getTargetContentType());
    }
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

  private ResponseEntity serveWithContentType(Resource resource, MediaType contentType) {
    try {
      return ResponseEntity.ok()
          .contentType(contentType)
          .contentLength(resource.contentLength())
          .body(resource);
    } catch (Exception e) {
      throw new RuntimeException("Error serving PDF API response", e);
    }
  }

}
