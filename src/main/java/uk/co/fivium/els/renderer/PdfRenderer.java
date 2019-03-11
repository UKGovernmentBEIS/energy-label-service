package uk.co.fivium.els.renderer;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;
import java.io.ByteArrayOutputStream;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class PdfRenderer implements Renderer {

  @Override
  public Resource render(Document html) {

    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(html.outerHtml(), "classpath://");
      builder.useSVGDrawer(new BatikSVGDrawer());
      builder.toStream(os);
      builder.run();

      return new ByteArrayResource(os.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Error rendering template to PDF", e);
    }
  }

}
