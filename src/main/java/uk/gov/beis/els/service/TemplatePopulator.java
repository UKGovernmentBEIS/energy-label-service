package uk.gov.beis.els.service;

import io.nayuki.qrcodegen.QrCode;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.beis.els.categories.common.*;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.categories.lamps.model.LampsFormPackagingArrow;
import uk.gov.beis.els.categories.lamps.model.LightSourceArrowOrientation;
import uk.gov.beis.els.categories.lamps.model.TemplateColour;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.util.FontUtils;
import uk.gov.beis.els.util.TemplateUtils;

public class TemplatePopulator {

  private static final Logger LOGGER = LoggerFactory.getLogger(TemplatePopulator.class);

  private static final String SVG_RATING_INCREMENT_ATTR_NAME = "data-rating-increment";
  private static final String SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME = "data-supplier-model-chars-per-row";
  private static final String TEXT_FONT_SIZE_ATTR_NAME = "data-font-size-px";
  private static final String TEXT_FONT_FACE_NAME = "data-font-face";

  private Document template;

  public TemplatePopulator(Document template) {
    this.template = template;
  }

  public TemplatePopulator setText(String elementId, String textValue) {
    TemplateUtils.getElementById(template, elementId).text(textValue);
    return this;
  }

  public TemplatePopulator setCondensingText(String elementId, String textValue) {
    // If an element has the textLength attribute, the text will always be stretched or shrunk to fit that width.
    // We only want it to shrink (condense), so we estimate if the rendered text will be wider than allowed by
    // textLength. If it will, we want it to shrink so we leave the attribute on. If it'll be narrower, we remove the
    // attribute because we don't want the text to expand.
    Element textElement = TemplateUtils.getElementById(template, elementId);

    float maxTextWidth = Float.parseFloat(TemplateUtils.getAttributeByName(textElement, "textLength"));
    float fontSize = Float.parseFloat(TemplateUtils.getAttributeByName(textElement, TEXT_FONT_SIZE_ATTR_NAME));
    String fontFace = TemplateUtils.getAttributeByName(textElement, TEXT_FONT_FACE_NAME);

    float estimatedTextWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(textValue, fontSize, fontFace);

    if(estimatedTextWidth < maxTextWidth) {
      textElement.removeAttr("textLength");
    }

    return setText(elementId, textValue);
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

  public TemplatePopulator setCondensingMultilineText(String elementId, String textValue) {
    Element line1 = TemplateUtils.getElementById(template,elementId + "Line1");
    Element line2 = TemplateUtils.getElementById(template,elementId + "Line2");

    textValue = textValue.trim();

    int charsPerRow = Integer.parseInt(TemplateUtils.getAttributeByName(TemplateUtils.getSvgElement(template), SVG_MULTILINE_CHARS_PER_ROW_ATTR_NAME));
    float maxTextWidth = Float.parseFloat(TemplateUtils.getAttributeByName(line1, "textLength"));
    float fontSize = Float.parseFloat(TemplateUtils.getAttributeByName(line1, TEXT_FONT_SIZE_ATTR_NAME));
    String fontFace = TemplateUtils.getAttributeByName(line1, TEXT_FONT_FACE_NAME);

    float estimatedTextWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(textValue, fontSize, fontFace);

    if(estimatedTextWidth < maxTextWidth) {
      // Text fits on one line without condensing
      line1.text(""); // clear out row
      line2.text(textValue);
      line2.removeAttr("textLength");
    } else {
      // Try wrapping using the chars per row specified on the template
      String wrappedText = WordUtils.wrap(textValue, charsPerRow, "\n", true);

      String [] lines = wrappedText.split("\n");

      if(lines.length == 1) {
        // It's possible textValue was greater than charsPerRow but the wrapped result is still only 1 line.
        // Seems to happen if textValue is 1 char longer than `charsPerRow` and this last char is a space.
        // This should be caught by the above .trim() but do a belt and braces check anyway
        lines = new String[]{"", lines[0]};
      }

      float line1EstimatedWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(lines[0], fontSize, fontFace);
      float line2EstimatedWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(lines[1], fontSize, fontFace);

      if(line1EstimatedWidth > maxTextWidth || line2EstimatedWidth > maxTextWidth || lines.length > 2) {
        // At least one line is too wide, or we have more than 2 lines, so split the text as evenly as possible
        // across two lines then apply condensing so it fits

        // Wrap text onto lines of about equal length
        String evenlyWrappedText = WordUtils.wrap(textValue, textValue.length()/2, "\n", true);
        lines = evenlyWrappedText.split("\n");

        // It might have wrapped onto more than 2 lines, so just make line 2 everything that didn't fit on line 1
        String line1Text = lines[0];
        String line2Text = textValue.substring(line1Text.length());

        // Recalculate the estimated widths
        line1EstimatedWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(line1Text, fontSize, fontFace);
        line2EstimatedWidth = FontUtils.INSTANCE.calculateEstimatedTextWidth(line2Text, fontSize, fontFace);

        if(line1EstimatedWidth <= maxTextWidth && line2EstimatedWidth <= maxTextWidth) {
          // We may have ended up forcing the text onto two lines because the chars per row wrapping put it on three lines
          // but the lines might still be less than the max width, so don't condense the text. This can happen if
          // the text contains lots of narrow characters.
          line1.removeAttr("textLength");
          line2.removeAttr("textLength");
        }
        else if(line1EstimatedWidth > line2EstimatedWidth) {
          // Change the textLength of the shorter line so that the characters are condensed by the same amount on both lines
          float lengthRatio = line2EstimatedWidth / line1EstimatedWidth;
          line2.attr("textLength", String.valueOf(maxTextWidth * lengthRatio));
        } else {
          float lengthRatio = line1EstimatedWidth / line2EstimatedWidth;
          line1.attr("textLength", String.valueOf(maxTextWidth * lengthRatio));
        }

        line1.text(line1Text);
        line2.text(line2Text);
      } else {
        // The text wrapped successfully using the chars per row value
        line1.text(lines[0]);
        line2.text(lines[1]);
        line1.removeAttr("textLength");
        line2.removeAttr("textLength");
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

  public TemplatePopulator setElementTranslate(String elementId, double x, double y) {
    Element svgElement = TemplateUtils.getElementById(template, elementId);
    svgElement.attr("transform", String.format("translate(%s,%s)", x, y));

    return this;
  }

  public TemplatePopulator setHoursMinutes(String elementId, String hours, String minutes) {
    int mins = Integer.parseInt(minutes);
    TemplateUtils.getElementById(template, elementId).text(String.format("%s:%02d", hours, mins));

    return this;
  }

  public TemplatePopulator setQrCode(BaseForm form) {
    Element qrCode = generateQrCode(form.getQrCodeUrl());
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
    if(!selectedRating.getPlusses().isEmpty()) {
      setText("ratingPlusses", selectedRating.getPlusses());
    }
    return this;
  }

  public TemplatePopulator transformPackagingArrow(RatingClass selectedRating, RatingClassRange ratingClassRange) {
    TemplateUtils.getElementById(template, "ratingArrow").addClass("rating" + calculateRatingColourIndex(selectedRating, ratingClassRange));
    TemplateUtils.getElementById(template, "ratingLetter").addClass("rating" + calculateRatingColourIndex(selectedRating, ratingClassRange));
    return this;
  }

  public Document getPopulatedDocument() {
    return template;
  }

  public <T extends AnalyticsForm & SupplierNameForm> ProcessedEnergyLabelDocument asProcessedEnergyLabel(
      ProductMetadata analyticsLabel, T form) {
    String analyticsAction = String.format("%s - %s", form.getSupplierName(), form.getModelName());
    String title = String.format("%s - %s", analyticsLabel.getProductFileName(), analyticsAction);
    TemplateUtils.getElementById(template, "html-title").text(title);

    return new ProcessedEnergyLabelDocument(template, analyticsLabel, form.getGoogleAnalyticsClientId(), analyticsAction);
  }

  public ProcessedEnergyLabelDocument asProcessedEnergyLabelNoSupplier(ProductMetadata analyticsLabel, AnalyticsForm form) {
    TemplateUtils.getElementById(template, "html-title").text(analyticsLabel.getProductFileName());
    return new ProcessedEnergyLabelDocument(template, analyticsLabel, form.getGoogleAnalyticsClientId(), "No supplier or model");
  }

  public ProcessedEnergyLabelDocument asProcessedEnergyLabelLampsPackagingArrow(ProductMetadata analyticsLabel, LampsFormPackagingArrow form) {
    String analyticsAction = String.format("%s - %s - %s",
        RatingClass.valueOf(form.getEfficiencyRating()).getDisplayValue(),
        LightSourceArrowOrientation.valueOf(form.getLabelOrientation()).getShortName(),
        TemplateColour.valueOf(form.getTemplateColour()).getDisplayName());

    String title = String.format("%s - %s", analyticsLabel.getProductFileName(), analyticsAction);
    TemplateUtils.getElementById(template, "html-title").text(title);
    return new ProcessedEnergyLabelDocument(template, analyticsLabel, form.getGoogleAnalyticsClientId(), analyticsAction);
  }

  public ProcessedInternetLabelDocument asProcessedInternetLabel(AnalyticsForm analyticsForm, InternetLabellingForm internetLabellingForm, String ratingClass, ProductMetadata label) {
    String analyticsAction;

    if(internetLabellingForm.getLabelColour() == null) {
      analyticsAction = String.format("%s - %s",
          RatingClass.valueOf(ratingClass).getDisplayValue(),
          InternetLabelOrientation.valueOf(internetLabellingForm.getLabelOrientation()).getShortName());
    } else {
      analyticsAction = String.format("%s - %s - %s",
          RatingClass.valueOf(ratingClass).getDisplayValue(),
          InternetLabelOrientation.valueOf(internetLabellingForm.getLabelOrientation()).getShortName(),
          InternetLabelColour.valueOf(internetLabellingForm.getLabelColour()).getDisplayName());
    }

    return new ProcessedInternetLabelDocument(template, ratingClass, label, analyticsForm.getGoogleAnalyticsClientId(), internetLabellingForm.getLabelFormat(), analyticsAction);
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
