package uk.gov.beis.els.util;

import org.assertj.core.data.Offset;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class FontUtilsTest {

  // Acceptable offset from expected result when doing floating point math
  private final Offset<Float> offset = within(0.01f);

  @Test
  public void testCalculateEstimatedTextWidth() {
    float width = FontUtils.INSTANCE.calculateEstimatedTextWidth("test", 10f, "Calibri Regular");
    assertThat(width).isCloseTo(15.585f, offset);
  }

  @Test
  public void testCalculateEstimatedTextWidth_missingChars() {
    String text = "test\u0201"; //Ȁ
    float width = FontUtils.INSTANCE.calculateEstimatedTextWidth(text, 10f, "Calibri Regular");
    assertThat(width).isCloseTo(24.48f, offset);
  }

  @Test
  public void testCalculateEstimatedTextWidth_withMultiCharUnicodeCodepoints() {
    String text = "test\uD83D\uDCA1"; //test💡
    float width = FontUtils.INSTANCE.calculateEstimatedTextWidth(text, 10f, "Calibri Regular");
    assertThat(width).isCloseTo(24.48f, offset);
  }

  @Test
  public void testCalculateEstimatedTextWidth_undefinedChars() {
    String text = "test\uffff";
    float width = FontUtils.INSTANCE.calculateEstimatedTextWidth(text, 10f, "Calibri Regular");
    assertThat(width).isCloseTo(24.48f, offset);
  }

  @Test
  public void testCalculateEstimatedTextWidth_rtlChars() {
    String text = "اختبار";
    float width = FontUtils.INSTANCE.calculateEstimatedTextWidth(text, 10f, "Calibri Regular");
    assertThat(width).isCloseTo(53.37f, offset);
  }

}
