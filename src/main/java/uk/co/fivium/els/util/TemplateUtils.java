package uk.co.fivium.els.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TemplateUtils {

  private static final String SVG_ELEMENT_ID = "Layer_1";

  public static Element getSvgElement(Document document){
   return getElementById(document, SVG_ELEMENT_ID);
  }

  public static String getAttributeByName(Element element, String attributeName) {
    String value = element.attr(attributeName);
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException(String.format("Attribute '%s' was empty or not set on element", attributeName));
    } else {
      return value;
    }
  }

  public static Element getElementById(Document document, String elementId) {
    Element element = document.getElementById(elementId);
    if (element != null) {
      return element;
    } else {
      throw new RuntimeException(String.format("No element with id '%s' found in document", elementId));
    }
  }

}
