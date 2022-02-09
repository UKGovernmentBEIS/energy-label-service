package uk.gov.beis.els.renderer;

import com.google.common.base.Stopwatch;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.util.TemplateUtils;

@Service
public class PngRenderer implements Renderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PngRenderer.class);

  @Override
  public Resource render(Document svg) {

    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(TemplateUtils.getSvgElement(svg).outerHtml(), "UTF-8"));

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      TranscoderOutput transcoderOutput = new TranscoderOutput(os);
      PNGTranscoder pngTranscoder = new PNGTranscoder();
      pngTranscoder.transcode(transcoderInput, transcoderOutput);

      LOGGER.info("PNG internet label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));

      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating PNG from SVG", e);
    }

  }

  @Override
  public MediaType getTargetContentType() {
    return MediaType.IMAGE_PNG;
  }

}
