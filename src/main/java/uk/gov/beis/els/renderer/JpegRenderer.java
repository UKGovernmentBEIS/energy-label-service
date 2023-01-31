package uk.gov.beis.els.renderer;

import com.google.common.base.Stopwatch;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
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
public class JpegRenderer implements Renderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(JpegRenderer.class);

  @Override
  public Resource render(Document svg) {
    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();
      ByteArrayOutputStream os = new ByteArrayOutputStream();

      writeJpeg(svg, os);

      LOGGER.info("JPEG label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));
      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating JPEG from SVG", e);
    }
  }

  @Override
  public Resource render(List<Document> documents) {
    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();

      ByteArrayOutputStream os = RendererUtils.applyImageCollation(
          documents,
          BufferedImage.TYPE_INT_RGB,
          "jpeg",
          this::writeJpeg
      );

      LOGGER.info("Collated JPEG label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));
      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating JPEG from SVG", e);
    }
  }

  @Override
  public MediaType getTargetContentType() {
    return MediaType.IMAGE_JPEG;
  }

  private void writeJpeg(Document svg, OutputStream outputStream) {
    try {
      Element svgElement = TemplateUtils.getSvgElement(svg);
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(svgElement.outerHtml(), "UTF-8"));
      TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);
      JPEGTranscoder jpegTranscoder = new JPEGTranscoder();
      jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);

      // If it's an energy label or fiche, set additional transcoder keys
      if (!"internet-label".equals(svgElement.attr("data-type"))) {
        RendererUtils.setRenderDimensions(jpegTranscoder, svg);
      }

      jpegTranscoder.transcode(transcoderInput, transcoderOutput);
    } catch (Exception e) {
      throw new RuntimeException("Error writing JPEG", e);
    }
  }

}
