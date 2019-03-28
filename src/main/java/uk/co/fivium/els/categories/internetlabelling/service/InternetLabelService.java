package uk.co.fivium.els.categories.internetlabelling.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.common.ProcessedInternetLabelDocument;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.renderer.JpegRenderer;
import uk.co.fivium.els.renderer.PngRenderer;
import uk.co.fivium.els.service.TemplatePopulator;
import uk.co.fivium.els.util.ControllerUtils;

@Service
public class InternetLabelService {

  // TODO delete once products have been moved over
  private final PngRenderer pngRenderer;
  private final JpegRenderer jpegRenderer;

  @Autowired
  public InternetLabelService(PngRenderer pngRenderer, JpegRenderer jpegRenderer) {
    this.pngRenderer = pngRenderer;
    this.jpegRenderer = jpegRenderer;
  }

  public ProcessedInternetLabelDocument generateInternetLabelHtml(InternetLabellingForm form, String ratingClass, LegislationCategory legislationCategory, ProductMetadata analyticsLabel) {
    Parser parser = Parser.htmlParser();
    parser.settings(ParseSettings.preserveCase);

    InternetLabelOrientation orientation = InternetLabelOrientation.valueOf(form.getLabelOrientation());
    String templatePath;
    if (orientation == InternetLabelOrientation.LEFT) {
      templatePath = "internet-labelling-left.svg";
    } else {
      templatePath = "internet-labelling-right.svg";
    }

    Document svgDom;

    try {
      svgDom = parser.parseInput(IOUtils.toString(new ClassPathResource("labels/internet-labelling/" + templatePath).getInputStream(), StandardCharsets.UTF_8.name()), "");
      svgDom.outputSettings().prettyPrint(false);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error loading internet label template '%s'", templatePath), e);
    }

    TemplatePopulator templatePopulator = new TemplatePopulator(svgDom);

    double height = Double.parseDouble(form.getProductPriceHeightPx());
    double scaleFactor = height/100;

    return templatePopulator
        .scaleSvg(scaleFactor)
        .transformInternetLabel(RatingClass.valueOf(ratingClass), legislationCategory.getPrimaryRatingRange())
        .asProcessedInternetLabel(form, form, ratingClass, analyticsLabel);
  }

  // TODO delete once products have been moved over
  public ResponseEntity generateInternetLabel(InternetLabellingForm form, String ratingClass, LegislationCategory legislationCategory, String filenamePrefix) {

    Document label = generateInternetLabelHtml(form, ratingClass, legislationCategory, ProductMetadata.LAMPS_FULL).getDocument();
    InternetLabelFormat format = InternetLabelFormat.valueOf(form.getLabelFormat());

    if (format == InternetLabelFormat.PNG) {
      return ControllerUtils.serveResource(pngRenderer.render(label), filenamePrefix + "-internet-label.png");
    } else {
      return ControllerUtils.serveResource(jpegRenderer.render(label), filenamePrefix + "-internet-label.jpg");
    }

  }

}
