package uk.gov.beis.els.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;

public class TemplatePopulatorTest {

  private TemplatePopulator templatePopulator;

  @Before
  public void setUp() {
    TemplateParserService templateParserService = new TemplateParserService();
    Document doc = templateParserService.parseTemplate("testEnergyLabel.svg");
    templatePopulator = new TemplatePopulator(doc);
  }

  @Test
  public void testSetText() {
    Document doc = templatePopulator.setText("textField", "foo").getPopulatedDocument();
    assertThat(doc.getElementById("textField").text()).isEqualTo("foo");
  }

  @Test
  public void testSetText_Escape() {
    Document doc = templatePopulator.setText("textField", "<script>foo</script>").getPopulatedDocument();
    assertThat(doc.getElementById("textField").html()).isEqualTo("&lt;script&gt;foo&lt;/script&gt;");
  }

  @Test
  public void testSetText_NoTarget() {
    assertThatThrownBy(() -> templatePopulator.setText("invalid", "foo"))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No element with id");
  }

  @Test
  public void testSetMultilineText() {
    // test template data-supplier-model-chars-per-row is 5
    Document doc = templatePopulator.setMultilineText("multilineTextField", "12345678").getPopulatedDocument();
    assertThat(doc.getElementById("multilineTextFieldLine1").text()).isEqualTo("12345");
    assertThat(doc.getElementById("multilineTextFieldLine2").text()).isEqualTo("678");
  }

  @Test
  public void testSetMultilineText_WordWrap() {
    Document doc = templatePopulator.setMultilineText("multilineTextField", "one two").getPopulatedDocument();
    assertThat(doc.getElementById("multilineTextFieldLine1").text()).isEqualTo("one");
    assertThat(doc.getElementById("multilineTextFieldLine2").text()).isEqualTo("two");
  }

  @Test
  public void testSetMultilineText_SingleLine() {
    Document doc = templatePopulator.setMultilineText("multilineTextField", "123").getPopulatedDocument();
    assertThat(doc.getElementById("multilineTextFieldLine1").text()).isBlank();
    assertThat(doc.getElementById("multilineTextFieldLine2").text()).isEqualTo("123");
  }

  @Test
  public void testApplyCssClassToId() {
    Document doc = templatePopulator.applyCssClassToId("textField", "foo").getPopulatedDocument();
    assertThat(doc.getElementById("textField").hasClass("foo")).isTrue();
  }

  @Test
  public void testApplyCssClassToId_KeepExisting() {
    Document doc = templatePopulator.applyCssClassToId("existingClass", "foo").getPopulatedDocument();
    assertThat(doc.getElementById("existingClass").classNames()).containsExactly("bar", "foo");
  }

  @Test
  public void testRemoveElementById() {
    Document doc = templatePopulator.removeElementById("textField").getPopulatedDocument();
    assertThat(doc.getElementById("textField")).isNull();
  }

  @Test
  public void testSetRatingArrow() {
    // test template data-rating-increment is 23.9357
    Document doc = templatePopulator.setRatingArrow("rating", RatingClass.A, RatingClassRange.of(RatingClass.APPP, RatingClass.D)).getPopulatedDocument();
    assertThat(doc.getElementById("rating").attr("transform")).isEqualTo("translate(0,71.8071)"); // data-rating-increment * 3
    assertThat(doc.getElementById("ratingLetter").text()).isEqualTo("A");
    assertThat(doc.getElementById("ratingPlusses").text()).isBlank();
  }

  @Test
  public void testSetRatingArrow_TopRating() {
    Document doc = templatePopulator.setRatingArrow("rating", RatingClass.APPP, RatingClassRange.of(RatingClass.APPP, RatingClass.D)).getPopulatedDocument();
    assertThat(doc.getElementById("rating").attr("transform")).isEqualTo("translate(0,0.0)");
    assertThat(doc.getElementById("ratingLetter").text()).isEqualTo("A");
    assertThat(doc.getElementById("ratingPlusses").text()).isEqualTo("+++");
  }

  @Test
  public void testApplyRatingCssClass() {
    Document doc = templatePopulator.applyRatingCssClass("textField", RatingClass.AP).getPopulatedDocument();
    assertThat(doc.getElementById("textField").hasClass("textFieldAP")).isTrue();
  }

}
