package uk.co.fivium.els.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.util.TemplateUtils;

public class TemplatePopulator {

  private static final String SVG_RATING_INCREMENT_ATTR_NAME = "data-rating-increment";
  private static final String SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME = "data-supplier-model-chars-per-row";

  private Document template;

  public TemplatePopulator(Document template) {
    this.template = template;
  }

  public TemplatePopulator setText(String elementId, String textValue) {
    TemplateUtils.getElementById(template, elementId).text(textValue); // TODO check this is escaping text
    return this;
  }

  public TemplatePopulator setMultilineText(String elementId, String textValue) {
    String charsPerRowAttr = TemplateUtils.getAttributeByName(TemplateUtils.getSvgElement(template), SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME);
    int charsPerRow = Integer.parseInt(charsPerRowAttr);

    Element line1 = TemplateUtils.getElementById(template,elementId + "Line1");
    Element line2 = TemplateUtils.getElementById(template,elementId + "Line2");

    // TODO try to split on space first to preserve words
    if (textValue.length() <= charsPerRow) {
      line1.text(""); // clear out row
      line2.text(textValue);
    } else {
      String line1Text = textValue.substring(0, charsPerRow);
      String line2Text = textValue.substring(charsPerRow);

      line1.text(line1Text);
      line2.text(line2Text);
    }

    return this;
  }

  public TemplatePopulator setRatingArrow(String parentElementId, RatingClass rating, RatingClassRange ratingClassRange) {
    String yAxisTransform = String.valueOf(calculateYAxisTransform(rating, ratingClassRange));

    Element svgGroupElement = TemplateUtils.getElementById(template, parentElementId);
    svgGroupElement.attr("transform", String.format("translate(0,%s)", yAxisTransform));

    setText(parentElementId+"Letter", rating.getLetter());
    setText(parentElementId+"Plusses", rating.getPlusses());

    return this;
  }

  public TemplatePopulator applyRatingCssClass(String elementId, RatingClass ratingClass) {
    TemplateUtils.getElementById(template, elementId).addClass(elementId + ratingClass.name());

    return this;
  }

  public TemplatePopulator applyCssClassToId(String elementId, String addedClass) {
    TemplateUtils.getElementById(template, elementId).addClass(addedClass);

    return this;
  }

  public TemplatePopulator removeElementById(String elementId) {
    TemplateUtils.getElementById(template, elementId).remove();

    return this;
  }

  public Document getPopulatedDocument() {
    return template;
  }

  private double getRatingIncrementValue() {
    String ratingIncrementAttr = TemplateUtils.getAttributeByName(TemplateUtils.getSvgElement(template), SVG_RATING_INCREMENT_ATTR_NAME);
    return Double.parseDouble(ratingIncrementAttr);
  }

  private double calculateYAxisTransform(RatingClass selectedRating, RatingClassRange ratingClassRange) {
    RatingClass maxRating = ratingClassRange.getHighestRating();

    if(selectedRating == maxRating) {
      return 0;
    } else {
      int incrementFactor = selectedRating.ordinal() - maxRating.ordinal();
      double ratingIncrementValue = getRatingIncrementValue();
      // multiply the attr value by the index
      return ratingIncrementValue * incrementFactor;
    }
  }


}
