package uk.co.fivium.els.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.fivium.els.categories.common.AnalyticsForm;
import uk.co.fivium.els.categories.common.ProcessedEnergyLabelDocument;
import uk.co.fivium.els.categories.common.ProcessedInternetLabelDocument;
import uk.co.fivium.els.categories.common.SupplierNameForm;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.co.fivium.els.model.ProductMetadata;
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
    return setRatingArrow(parentElementId, rating, ratingClassRange, SVG_RATING_INCREMENT_ATTR_NAME);
  }

  public TemplatePopulator setRatingArrow(String parentElementId, RatingClass rating, RatingClassRange ratingClassRange, String ratingIncrementAttrName) {
    double ratingIncrementValue = getRatingIncrementValue(ratingIncrementAttrName);
    String yAxisTransform = String.valueOf(calculateYAxisTransform(rating, ratingClassRange, ratingIncrementValue));

    Element svgGroupElement = TemplateUtils.getElementById(template, parentElementId);
    svgGroupElement.attr("transform", String.format("translate(0,%s)", yAxisTransform));

    setText(parentElementId+"Letter", rating.getLetter());
    if (template.getElementById(parentElementId + "Plusses") != null) {
      setText(parentElementId + "Plusses", rating.getPlusses());
    }

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

  public TemplatePopulator scaleSvg(double scaleFactor) {

    int width = Integer.parseInt(TemplateUtils.getSvgElement(template).attr("width"));
    int height = Integer.parseInt(TemplateUtils.getSvgElement(template).attr("height"));

    double scaledWidth = width * scaleFactor;
    double scaledHeight = height * scaleFactor;

    TemplateUtils.getSvgElement(template).attr("width", String.valueOf(scaledWidth));
    TemplateUtils.getSvgElement(template).attr("height", String.valueOf(scaledHeight));

    return this;
  }

  public TemplatePopulator transformInternetLabel(RatingClass selectedRating, RatingClassRange ratingClassRange) {
   TemplateUtils.getElementById(template, "ratingArrow").addClass("rating" + calculateRatingColourIndex(selectedRating, ratingClassRange));
    setText("ratingLetter", selectedRating.getLetter());
    setText("ratingPlusses", selectedRating.getPlusses());
   return this;
  }

  public Document getPopulatedDocument() {
    return template;
  }

  public ProcessedEnergyLabelDocument asProcessedEnergyLabel(ProductMetadata analyticsLabel, AnalyticsForm analyticsForm, SupplierNameForm supplierNameForm) {
    return new ProcessedEnergyLabelDocument(template, analyticsLabel, analyticsForm.getGoogleAnalyticsClientId(), supplierNameForm.getSupplierName(), supplierNameForm.getModelName());
  }

  public ProcessedEnergyLabelDocument asProcessedEnergyLabel(ProductMetadata analyticsLabel, AnalyticsForm analyticsForm) {
    return new ProcessedEnergyLabelDocument(template, analyticsLabel, analyticsForm.getGoogleAnalyticsClientId(), null, null);
  }

  public ProcessedInternetLabelDocument asProcessedInternetLabel(AnalyticsForm analyticsForm, InternetLabellingForm internetLabellingForm, String ratingClass, ProductMetadata label) {
    return new ProcessedInternetLabelDocument(template, ratingClass, label, analyticsForm.getGoogleAnalyticsClientId(), internetLabellingForm.getLabelFormat());
  }

  private double getRatingIncrementValue(String ratingIncrementAttrName) {
    String ratingIncrementAttr = TemplateUtils.getAttributeByName(TemplateUtils.getSvgElement(template), ratingIncrementAttrName);
    return Double.parseDouble(ratingIncrementAttr);
  }

  private double calculateYAxisTransform(RatingClass selectedRating, RatingClassRange ratingClassRange, double ratingIncrementValue) {
    RatingClass maxRating = ratingClassRange.getHighestRating();

    if(selectedRating == maxRating) {
      return 0;
    } else {
      int incrementFactor = selectedRating.ordinal() - maxRating.ordinal();
      // multiply the attr value by the index
      return ratingIncrementValue * incrementFactor;
    }
  }

  private int calculateRatingColourIndex(RatingClass selectedRating, RatingClassRange ratingClassRange) {
    RatingClass maxRating = ratingClassRange.getHighestRating();

    if(selectedRating == maxRating) {
      return 0;
    } else {
      int index = selectedRating.ordinal() - maxRating.ordinal();
      // Cap index at 6, any 'lower' ratings are the same Red colour
      if (index > 6) {
        index = 6;
      }
      return index;
    }

  }

}
