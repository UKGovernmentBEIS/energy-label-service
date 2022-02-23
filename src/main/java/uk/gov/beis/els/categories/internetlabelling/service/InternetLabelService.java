package uk.gov.beis.els.categories.internetlabelling.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class InternetLabelService {

  public ProcessedInternetLabelDocument generateInternetLabel(BaseInternetLabelApiForm apiForm, String ratingClass, LegislationCategory legislationCategory, ProductMetadata analyticsLabel) {
    InternetLabellingForm standardForm = new InternetLabellingForm();
    standardForm.setLabelFormat(apiForm.getLabelFormat().name());
    standardForm.setLabelOrientation(apiForm.getLabelOrientation().name());
    standardForm.setProductPriceHeightPx(String.valueOf(apiForm.getProductPriceHeightPx()));
    return generateInternetLabel(standardForm, ratingClass, legislationCategory, analyticsLabel);
  }

  public ProcessedInternetLabelDocument generateInternetLabel(RescaledInternetLabelApiForm apiForm, String ratingClass, LegislationCategory legislationCategory, ProductMetadata analyticsLabel) {
    InternetLabellingForm standardForm = new InternetLabellingForm();
    standardForm.setLabelFormat(apiForm.getLabelFormat().name());
    standardForm.setLabelOrientation(apiForm.getLabelOrientation().name());

    InternetLabelColour colour;
    if(legislationCategory.getInternetLabelTemplate().getHasBWOption()) {
      colour = apiForm.getLabelColour();
    } else {
      colour = InternetLabelColour.COLOUR;
    }
    standardForm.setLabelColour(colour.name());

    standardForm.setProductPriceHeightPx(String.valueOf(apiForm.getProductPriceHeightPx()));
    return generateInternetLabel(standardForm, ratingClass, legislationCategory, analyticsLabel);
  }

  public ProcessedInternetLabelDocument generateInternetLabel(InternetLabellingForm form, String ratingClass, LegislationCategory legislationCategory, ProductMetadata analyticsLabel) {
    Parser parser = Parser.htmlParser();
    parser.settings(ParseSettings.preserveCase);

    InternetLabelOrientation orientation = InternetLabelOrientation.valueOf(form.getLabelOrientation());
    InternetLabelColour colour;
    if(legislationCategory.getInternetLabelTemplate().getHasBWOption()) {
      colour = InternetLabelColour.valueOf(form.getLabelColour());
    } else {
      colour = InternetLabelColour.COLOUR;
    }

    String templatePath = legislationCategory.getInternetLabelTemplate().getTemplatePathForOrientationAndColour(orientation, colour);

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
        .transformInternetLabel(RatingClass.getEnum(ratingClass), legislationCategory.getPrimaryRatingRange())
        .asProcessedInternetLabel(form, form, ratingClass, analyticsLabel);
  }

}
