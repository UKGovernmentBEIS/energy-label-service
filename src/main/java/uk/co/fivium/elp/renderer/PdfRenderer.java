package uk.co.fivium.elp.renderer;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uk.co.fivium.elp.model.TvForm;

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

  @Override
  public Resource render(TvForm form) throws Exception {


    ByteArrayOutputStream os = new ByteArrayOutputStream();

//    String template = IOUtils.toString(new ClassPathResource("tv-template2.html").getInputStream(), StandardCharsets.UTF_8.name());


    String template = IOUtils.toString(new ClassPathResource("air-con.html").getInputStream(), StandardCharsets.UTF_8.name());
    Parser parser = Parser.htmlParser();
    parser.settings(new ParseSettings(true, true)); //

    Document templateDom = parser.parseInput(template, "");

//    templateDom.getElementById("supplier").text(form.getManufacturer());
//    templateDom.getElementById("model").text(form.getModelName());
//    templateDom.getElementById("watt").text(form.getPowerConsumption());
//    templateDom.getElementById("kwhAnnum").text(form.getAnnualPowerConsumption());
//    templateDom.getElementById("cm").text(form.getScreenSizeCm());
//    templateDom.getElementById("inch").text(form.getScreenSizeIn());

    PdfRendererBuilder builder = new PdfRendererBuilder();
//    builder.withHtmlContent(templateDom.outerHtml(), "classpath://");
    builder.withUri("classpath:///air-con.html");
    builder.useSVGDrawer(new BatikSVGDrawer());
    builder.toStream(os);
    builder.run();

    return new ByteArrayResource(os.toByteArray());
  }
}
