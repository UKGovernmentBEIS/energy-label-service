package uk.gov.beis.els.renderer;

import com.google.common.base.Stopwatch;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.util.TemplateUtils;

@Service
public class JpegRenderer implements Renderer {

  private static final Logger LOGGER = LoggerFactory.getLogger(JpegRenderer.class);

  @Override
  public Resource render(Document svg) {
    try {
      Stopwatch renderStopwatch = Stopwatch.createStarted();
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(TemplateUtils.getSvgElement(svg).outerHtml(), "UTF-8"));

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      TranscoderOutput transcoderOutput = new TranscoderOutput(os);
      JPEGTranscoder jpegTranscoder= new JPEGTranscoder();
      jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);
      jpegTranscoder.transcode(transcoderInput, transcoderOutput);

      LOGGER.info("JPEG internet label generated. Took [{}ms]", renderStopwatch.elapsed(TimeUnit.MILLISECONDS));

      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating JPEG from SVG", e);
    }
  }
}
