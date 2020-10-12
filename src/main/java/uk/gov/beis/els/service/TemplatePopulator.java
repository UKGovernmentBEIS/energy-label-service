package uk.gov.beis.els.service;

import io.nayuki.qrcodegen.QrCode;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.beis.els.categories.common.AnalyticsForm;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.common.SupplierNameForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.util.TemplateUtils;

public class TemplatePopulator {

  private static final Logger LOGGER = LoggerFactory.getLogger(TemplatePopulator.class);

  private static final String SVG_RATING_INCREMENT_ATTR_NAME = "data-rating-increment";
  private static final String SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME = "data-supplier-model-chars-per-row";

  private Document template;

  public TemplatePopulator(Document template) {
    this.template = template;
  }

  public TemplatePopulator setText(String elementId, String textValue) {
    TemplateUtils.getElementById(template, elementId).text(textValue);
    return this;
  }

  public TemplatePopulator setMultilineText(String elementId, String textValue) {
    String charsPerRowAttr = TemplateUtils.getAttributeByName(TemplateUtils.getSvgElement(template), SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME);
    int charsPerRow = Integer.parseInt(charsPerRowAttr);

    Element line1 = TemplateUtils.getElementById(template,elementId + "Line1");
    Element line2 = TemplateUtils.getElementById(template,elementId + "Line2");

    textValue = textValue.trim();

    if (textValue.length() <= charsPerRow) {
      line1.text(""); // clear out row
      line2.text(textValue);
    } else {
      String wrappedText = WordUtils.wrap(textValue, charsPerRow, "\n", true);

      String[] lines = wrappedText.split("\n");

      if (lines.length != 2) {
        LOGGER.warn("Failed to wrap text '{}' into 2 rows of {} chars", textValue, charsPerRow);
      }

      if (lines.length == 1) {
        // It's possible textValue was greater than charsPerRow but the wrapped result is still only 1 line.
        // Seems to happen if textValue is 1 char longer than `charsPerRow` and this last char is a space.
        // This should be caught by the above .trim() but do a belt and braces check anyway
        line1.text(""); // clear out row
        line2.text(textValue);
      } else {
        line1.text(lines[0]);
        line2.text(lines[1]);
      }

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

  public TemplatePopulator setHoursMinutes(String elementId, String hours, String minutes) {
    int mins = Integer.parseInt(minutes);
    TemplateUtils.getElementById(template, elementId).text(String.format("%s:%02d", hours, mins));
    return this;
  }

  public TemplatePopulator setQrCode(String url) {
    Element qrCode = generateQrCode(url);
    Element qrCodeTemplateDom = TemplateUtils.getElementById(template, "qrCode");
    Element qrCodePlaceholder = TemplateUtils.getElementByTag(qrCodeTemplateDom, "rect");

    // Read position infromation off the placeholder
    String width = qrCodePlaceholder.attr("width");
    String height = qrCodePlaceholder.attr("height");
    String xPos = qrCodePlaceholder.attr("x");
    String yPos = qrCodePlaceholder.attr("y");

    // Adjust the generated QR code and template to match the required position/scale
    qrCode
        .attr("width", width)
        .attr("height", height);

    qrCodeTemplateDom
        .attr("transform", String.format("translate(%s,%s)", xPos, yPos));

    qrCodeTemplateDom
        .empty() // Remove placeholder rectangle and text
        .appendChild(qrCode);

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

  public <T extends AnalyticsForm & SupplierNameForm> ProcessedEnergyLabelDocument asProcessedEnergyLabel(
      ProductMetadata analyticsLabel, T form) {
    String title = String.format(analyticsLabel.getProductFileName() + " - %s - %s",
      form.getSupplierName(), form.getModelName());
    TemplateUtils.getElementById(template, "html-title").text(title);

    return new ProcessedEnergyLabelDocument(template, analyticsLabel, form.getGoogleAnalyticsClientId(), form.getSupplierName(), form.getModelName());
  }

  public ProcessedEnergyLabelDocument asProcessedEnergyLabelNoSupplier(ProductMetadata analyticsLabel, AnalyticsForm form) {
    TemplateUtils.getElementById(template, "html-title").text(analyticsLabel.getProductFileName());
    return new ProcessedEnergyLabelDocument(template, analyticsLabel, form.getGoogleAnalyticsClientId(), null, null);
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

    if(selectedRating.equals(maxRating)) {
      return 0;
    } else {
      int incrementFactor = selectedRating.ordinal() - maxRating.ordinal();
      // multiply the attr value by the index
      return ratingIncrementValue * incrementFactor;
    }
  }

  private int calculateRatingColourIndex(RatingClass selectedRating, RatingClassRange ratingClassRange) {
    RatingClass maxRating = ratingClassRange.getHighestRating();

    if(selectedRating.equals(maxRating)) {
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

  private Element generateQrCode(String url) {
    Parser parser = Parser.htmlParser();
    parser.settings(ParseSettings.preserveCase);

    QrCode qrCode = QrCode.encodeText(url, QrCode.Ecc.LOW);

    Document svgDocument = parser.parseInput(qrCode.toSvgString(0), "");

    return TemplateUtils.getElementByTag(svgDocument, "svg");
  }

}
