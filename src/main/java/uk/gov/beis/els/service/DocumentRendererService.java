package uk.gov.beis.els.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.model.EnergyLabelFormat;
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

  public ResponseEntity processResponse(ProcessedEnergyLabelDocument processedDocument) {
    return processResponse(processedDocument, GoogleAnalyticsEventCategory.ENERGY_LABEL);
  }

  public ResponseEntity processResponse(ProcessedEnergyLabelDocument processedDocument, GoogleAnalyticsEventCategory analyticsCategory) {
    return processResponse(processedDocument, ResponseType.ATTACHMENT, analyticsCategory);
  }

  public ResponseEntity processResponse(List<ProcessedEnergyLabelDocument> processedDocuments) {
    return processResponse(processedDocuments, GoogleAnalyticsEventCategory.ENERGY_LABEL);
  }

  public ResponseEntity processResponse(List<ProcessedEnergyLabelDocument> processedDocuments, GoogleAnalyticsEventCategory analyticsCategory) {
    return processResponse(processedDocuments, ResponseType.ATTACHMENT, analyticsCategory);
  }

  public ResponseEntity processApiResponse(ProcessedEnergyLabelDocument processedDocument) {
    return processApiResponse(processedDocument, GoogleAnalyticsEventCategory.ENERGY_LABEL_API);
  }

  public ResponseEntity processApiResponse(ProcessedEnergyLabelDocument processedDocument, GoogleAnalyticsEventCategory analyticsCategory) {
    return processResponse(processedDocument, ResponseType.DIRECT, analyticsCategory);
  }

  public ResponseEntity processApiResponse(List<ProcessedEnergyLabelDocument> processedDocuments) {
    return processApiResponse(processedDocuments, GoogleAnalyticsEventCategory.ENERGY_LABEL_API);
  }

  public ResponseEntity processApiResponse(List<ProcessedEnergyLabelDocument> processedDocuments, GoogleAnalyticsEventCategory analyticsCategory) {
    return processResponse(processedDocuments, ResponseType.DIRECT, analyticsCategory);
  }

  public ResponseEntity processInternetLabelResponse(ProcessedInternetLabelDocument processedDocument) {
    return processImage(processedDocument, ResponseType.ATTACHMENT, GoogleAnalyticsEventCategory.INTERNET_LABEL);
  }

  public ResponseEntity processInternetLabelApiResponse(ProcessedInternetLabelDocument processedDocument) {
    return processImage(processedDocument, ResponseType.DIRECT, GoogleAnalyticsEventCategory.INTERNET_LABEL_API);
  }

  public ResponseEntity processResponse(ProcessedEnergyLabelDocument processedDocument, ResponseType responseType, GoogleAnalyticsEventCategory eventCategory) {
    Renderer renderer = getRenderer(processedDocument.getLabelFormat());

    Resource resource = renderer.render(processedDocument.getDocument());

    analyticsService.sendGoogleAnalyticsEvent(processedDocument.getClientAnalyticsToken(),
        eventCategory,
        processedDocument.getAnalyticsEventParams());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(resource, generateFilename(processedDocument));
    } else {
      return serveWithContentType(resource, renderer.getTargetContentType());
    }

  }

  private ResponseEntity processResponse(List<ProcessedEnergyLabelDocument> processedDocuments, ResponseType responseType, GoogleAnalyticsEventCategory eventCategory) {
    List<Document> htmlDocuments = processedDocuments.stream()
        .map(ProcessedEnergyLabelDocument::getDocument)
        .collect(Collectors.toList());

    ProcessedEnergyLabelDocument primaryDocument = processedDocuments.get(0);

    Renderer renderer = getRenderer(primaryDocument.getLabelFormat());

    Resource resource = renderer.render(htmlDocuments);

    // Use the first document for metadata
    analyticsService.sendGoogleAnalyticsEvent(primaryDocument.getClientAnalyticsToken(),
        eventCategory,
        primaryDocument.getAnalyticsEventParams());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(resource, generateFilename(primaryDocument));
    } else {
      return serveWithContentType(resource, renderer.getTargetContentType());
    }
  }

  private Renderer getRenderer(EnergyLabelFormat format) {
    switch (format) {
      case PDF:
        return pdfRenderer;
      case PNG:
        return pngRenderer;
      case JPEG:
        return jpegRenderer;
      default:
        throw new RuntimeException("No renderer found for format " + format);
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
        processedDocument.getAnalyticsEventParams());

    if (responseType == ResponseType.ATTACHMENT) {
      return serveResource(renderer.render(processedDocument.getDocument()), generateImageFilename(processedDocument));
    } else {
      return serveWithContentType(renderer.render(processedDocument.getDocument()), renderer.getTargetContentType());
    }
  }

  private String generateFilename(ProcessedEnergyLabelDocument processedDocument) {
    String filename = processedDocument.getDocument().title() + processedDocument.getLabelFormat().getFileExtension();
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
              HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build().toString())
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
