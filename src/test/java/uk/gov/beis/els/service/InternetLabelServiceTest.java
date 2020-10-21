package uk.gov.beis.els.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import uk.gov.beis.els.categories.common.ProcessedInternetLabelDocument;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;

public class InternetLabelServiceTest {

  private InternetLabellingForm internetLabellingForm;

  @Before
  public void setUp() {
    internetLabellingForm = new InternetLabellingForm();
    internetLabellingForm.setLabelFormat(InternetLabelFormat.PNG.name());
    internetLabellingForm.setLabelOrientation(InternetLabelOrientation.LEFT.name());
    internetLabellingForm.setProductPriceHeightPx("500");
    internetLabellingForm.setGoogleAnalyticsClientId("123");
    internetLabellingForm.setLabelColour(InternetLabelColour.COLOUR.name());
  }

  @Test
  public void testGenerateInternetLabel() {
    InternetLabelService internetLabelService = new InternetLabelService();
    ProcessedInternetLabelDocument doc = internetLabelService.generateInternetLabel(internetLabellingForm, RatingClass.B.name(), LegislationCategory.of(
        RatingClassRange.of(RatingClass.AP, RatingClass.D)), ProductMetadata.DISHWASHERS);

    assertThat(doc.getClientAnalyticsToken()).isEqualTo("123");
    assertThat(doc.getInternetLabelFormat()).isEqualTo(InternetLabelFormat.PNG);
    assertThat(doc.getRatingClass()).isEqualTo(RatingClass.B);
    assertThat(doc.getProductMetadata()).isEqualTo(ProductMetadata.DISHWASHERS);


    Document processedLabel = doc.getDocument();

    assertThat(processedLabel.getElementsByTag("title").get(0).text()).isEqualTo("internet-labelling-left");

    // scaling factor is 5 (500/100), w/h should be 5x original value
    assertThat(processedLabel.getElementById("Layer_1").attr("width")).isEqualTo("1955.0");
    assertThat(processedLabel.getElementById("Layer_1").attr("height")).isEqualTo("790.0");
    assertThat(processedLabel.getElementById("ratingLetter").text()).isEqualTo("B");
    assertThat(processedLabel.getElementById("ratingPlusses").text()).isBlank();
    assertThat(processedLabel.getElementById("ratingArrow").hasClass("rating2")).isTrue();
  }

  @Test
  public void testGenerateInternetLabel_TopRating() {
    InternetLabelService internetLabelService = new InternetLabelService();

    internetLabellingForm.setLabelOrientation(InternetLabelOrientation.RIGHT.name());

    ProcessedInternetLabelDocument doc = internetLabelService.generateInternetLabel(internetLabellingForm, RatingClass.AP.name(), LegislationCategory.of(
        RatingClassRange.of(RatingClass.AP, RatingClass.D)), ProductMetadata.DISHWASHERS);

    assertThat(doc.getClientAnalyticsToken()).isEqualTo("123");
    assertThat(doc.getInternetLabelFormat()).isEqualTo(InternetLabelFormat.PNG);
    assertThat(doc.getRatingClass()).isEqualTo(RatingClass.AP);
    assertThat(doc.getProductMetadata()).isEqualTo(ProductMetadata.DISHWASHERS);


    Document processedLabel = doc.getDocument();

    assertThat(processedLabel.getElementsByTag("title").get(0).text()).isEqualTo("internet-labelling-right");

    // scaling factor is 5 (500/100), w/h should be 5x original value
    assertThat(processedLabel.getElementById("Layer_1").attr("width")).isEqualTo("1955.0");
    assertThat(processedLabel.getElementById("Layer_1").attr("height")).isEqualTo("790.0");
    assertThat(processedLabel.getElementById("ratingLetter").text()).isEqualTo("A");
    assertThat(processedLabel.getElementById("ratingPlusses").text()).isEqualTo("+");
    assertThat(processedLabel.getElementById("ratingArrow").hasClass("rating0")).isTrue();
  }


}
