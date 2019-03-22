package uk.co.fivium.els.renderer;

import java.io.ByteArrayOutputStream;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.util.TemplateUtils;

@Service
public class JpegRenderer implements Renderer {

  @Override
  public Resource render(Document svg) {
    try {
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(TemplateUtils.getSvgElement(svg).outerHtml(), "UTF-8"));

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      TranscoderOutput transcoderOutput = new TranscoderOutput(os);
      JPEGTranscoder jpegTranscoder= new JPEGTranscoder();
      jpegTranscoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 1f);
      jpegTranscoder.transcode(transcoderInput, transcoderOutput);

      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating JPEG from SVG", e);
    }
  }
}
