package uk.co.fivium.elp.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.fivium.elp.model.RatingClass;
import uk.co.fivium.elp.model.RatingClassRange;

public class TemplateUtils {

  private static final String SVG_ELEMENT_ID = "Layer_1";
  private static final String SVG_RATING_INCREMENT_ATTR_NAME = "data-rating-increment";

  public static Element getSvgElement(Document document){
   return getElementById(document, SVG_ELEMENT_ID);
  }

  private static double getRatingIncrementValue(Document document) {
    String ratingIncrementAttr = getAttributeByName(getSvgElement(document), SVG_RATING_INCREMENT_ATTR_NAME);
    return Double.parseDouble(ratingIncrementAttr);
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

  public static void setText(Document document, String elementId, String textValue) {
    TemplateUtils.getElementById(document, elementId).text(textValue); // TODO check this is escaping text
  }

  public static void setRatingArrow(Document document, String parentElementId, RatingClass rating, RatingClassRange ratingClassRange) {
    String yAxisTransform = String.valueOf(calculateYAxisTransform(document, rating, ratingClassRange));

    Element svgGroupElement = TemplateUtils.getElementById(document, parentElementId);
    svgGroupElement.attr("transform", String.format("translate(0,%s)", yAxisTransform));

    setText(document, parentElementId+"Letter", rating.getLetter());
    setText(document, parentElementId+"Plusses", rating.getPlusses());
  }

  private static double calculateYAxisTransform(Document document, RatingClass selectedRating, RatingClassRange ratingClassRange) {
    RatingClass maxRating = ratingClassRange.getHighestRating();

    if(selectedRating == maxRating) {
      return 0;
    } else {
      int incrementFactor = selectedRating.ordinal() - maxRating.ordinal();
      double ratingIncrementValue = TemplateUtils.getRatingIncrementValue(document);
      // multiply the attr value by the index
      return ratingIncrementValue * incrementFactor;
    }
  }

  public static void applyRatingCssClass(Document document, String elementId, RatingClass ratingClass) {
    getElementById(document, elementId).addClass(elementId + ratingClass.name());
  }

}
