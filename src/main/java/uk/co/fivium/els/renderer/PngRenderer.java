package uk.co.fivium.els.renderer;

import java.io.ByteArrayOutputStream;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.util.TemplateUtils;

@Service
public class PngRenderer implements Renderer {

  @Override
  public Resource render(Document svg) {

    try {
      TranscoderInput transcoderInput = new TranscoderInput(IOUtils.toInputStream(TemplateUtils.getSvgElement(svg).outerHtml(), "UTF-8"));

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      TranscoderOutput transcoderOutput = new TranscoderOutput(os);
      PNGTranscoder pngTranscoder = new PNGTranscoder();
      pngTranscoder.transcode(transcoderInput, transcoderOutput);

      return new ByteArrayResource(os.toByteArray());

    } catch (Exception e) {
      throw new RuntimeException("Error generating PNG from SVG", e);
    }

  }
}
