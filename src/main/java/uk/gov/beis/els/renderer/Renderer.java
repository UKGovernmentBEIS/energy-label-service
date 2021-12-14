package uk.gov.beis.els.renderer;

import org.jsoup.nodes.Document;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public interface Renderer {

  Resource render(Document html);

  MediaType getTargetContentType();

}
