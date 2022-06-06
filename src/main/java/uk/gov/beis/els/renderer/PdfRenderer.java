package uk.gov.beis.els.renderer;

import com.google.common.base.Stopwatch;
import com.openhtmltopdf.pdfboxout.PdfBoxRenderer;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class PdfRenderer implements Renderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PdfRenderer.class);

  @Override
  public Resource render(Document html) {

    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

      Stopwatch renderStopwatch = Stopwatch.createStarted();

      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(html.outerHtml(), "classpath://");
      builder.useSVGDrawer(new BatikSVGDrawer());
      builder.toStream(os);
      builder.run();

      LOGGER.info("PDF label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));

      return new ByteArrayResource(os.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Error rendering template to PDF", e);
    }
  }

  public Resource render(List<Document> documents) {

    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

      Stopwatch renderStopwatch = Stopwatch.createStarted();

      PDDocument collatedDocument = new PDDocument();
      PdfRendererBuilder builder = new PdfRendererBuilder();

      for(Document document : documents) {
        builder.withHtmlContent(document.outerHtml(), "classpath://");
        builder.useSVGDrawer(new BatikSVGDrawer());
        builder.usePDDocument(collatedDocument);

        PdfBoxRenderer renderer = builder.buildPdfRenderer();
        renderer.layout();

        // Keep the doc open but close the renderer
        renderer.createPDFWithoutClosing();
        renderer.close();
      }

      collatedDocument.save(os);

      LOGGER.info("Collated PDF label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));

      return new ByteArrayResource(os.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Error rendering collated templates to PDF", e);
    }
  }

  @Override
  public MediaType getTargetContentType() {
    return MediaType.APPLICATION_PDF;
  }

}
