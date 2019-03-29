package uk.co.fivium.els.categories.internetlabelling.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.common.ProcessedInternetLabelDocument;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class InternetLabelService {

  public ProcessedInternetLabelDocument generateInternetLabel(InternetLabellingForm form, String ratingClass, LegislationCategory legislationCategory, ProductMetadata analyticsLabel) {
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

}
