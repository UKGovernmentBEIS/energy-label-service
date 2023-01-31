package uk.gov.beis.els.renderer;

import com.google.common.base.Stopwatch;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.util.RendererUtils;
import uk.gov.beis.els.util.TemplateUtils;

@Service
public class PngRenderer implements Renderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PngRenderer.class);

  @Override
  public Resource render(Document svg) {

    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();
      ByteArrayOutputStream os = new ByteArrayOutputStream();

      writePng(svg, os);

      LOGGER.info("PNG internet label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));
      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating PNG from SVG", e);
    }

  }

  @Override
  public Resource render(List<Document> documents) {
    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();

      ByteArrayOutputStream os = RendererUtils.applyImageCollation(
          documents,
          BufferedImage.TYPE_INT_ARGB,
          "png",
          this::writePng
      );

      LOGGER.info("Collated PNG label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));
      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating PNG from SVG", e);
    }
  }

  @Override
  public MediaType getTargetContentType() {
    return MediaType.IMAGE_PNG;
  }

  private void writePng(Document svg, OutputStream outputStream) {
    try {
      Element svgElement = TemplateUtils.getSvgElement(svg);
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(svgElement.outerHtml(), "UTF-8"));
      TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);
      PNGTranscoder pngTranscoder = new PNGTranscoder();

      // If it's an energy label or fiche, set additional transcoder keys
      if (!"internet-label".equals(svgElement.attr("data-type"))) {
        pngTranscoder.addTranscodingHint(ImageTranscoder.KEY_BACKGROUND_COLOR, Color.WHITE);
        RendererUtils.setRenderDimensions(pngTranscoder, svg);
      }

      pngTranscoder.transcode(transcoderInput, transcoderOutput);
    } catch (Exception e) {
      throw new RuntimeException("Error writing PNG", e);
    }

  }

}
