package uk.co.fivium.elp.service;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.co.fivium.elp.util.TemplateUtils;

@Service
public class TemplateParserService {

  public Document parseTemplate(String svgPath) {

    try {

      Parser parser = createParser();

      Document svgDom = parser.parseInput(IOUtils.toString(new ClassPathResource(svgPath).getInputStream(), StandardCharsets.UTF_8.name()), "");
      Document template = parser.parseInput(populateDimensions(svgDom), "");
      template.outputSettings().prettyPrint(false);

      TemplateUtils.getElementById(template, "root").appendChild(TemplateUtils.getSvgElement(svgDom));

      return template;
    } catch (Exception e) {
      throw new RuntimeException(String.format("Error parsing template file for svg path '%s'", svgPath), e);
    }

  }

  /**
   * // TODO
   * @param svgDom
   * @return
   * @throws IOException
   */
  private String populateDimensions(Document svgDom) throws IOException {
    String htmlWrapper = IOUtils.toString(new ClassPathResource("labels/html-wrapper.html").getInputStream(), StandardCharsets.UTF_8.name());
    Dimension pageDimensions = parsePageDimension(svgDom);
    return injectPageDimensions(htmlWrapper, pageDimensions);
  }

  private Parser createParser() {
    Parser parser = Parser.htmlParser();
    parser.settings(ParseSettings.preserveCase);
    return parser;
  }

  private Dimension parsePageDimension(Document svgDom) {
    Element svgElement = TemplateUtils.getSvgElement(svgDom);

    int width = parseDimensionAttribute(TemplateUtils.getAttributeByName(svgElement, "width"));
    int height = parseDimensionAttribute(TemplateUtils.getAttributeByName(svgElement, "height"));
    // remove attrs after parsing as the render does not handle the units being specified (and the attrs are not required anyway)
    svgElement.removeAttr("width");
    svgElement.removeAttr("height");

    return new Dimension(width, height);

  }

  private int parseDimensionAttribute(String attr) {
    return Integer.parseInt(StringUtils.substringBefore(attr, "mm"));
  }

  private String injectPageDimensions(String htmlWrapper, Dimension pageDimension) {
    return htmlWrapper
        .replace("${width}", String.valueOf(pageDimension.getWidth()))
        .replace("${height}", String.valueOf(pageDimension.getHeight()));
  }

}
