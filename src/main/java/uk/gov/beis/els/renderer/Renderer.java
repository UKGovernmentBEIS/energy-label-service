package uk.gov.beis.els.renderer;

import org.jsoup.nodes.Document;
import org.springframework.core.io.Resource;

public interface Renderer {

  Resource render(Document html);

}
