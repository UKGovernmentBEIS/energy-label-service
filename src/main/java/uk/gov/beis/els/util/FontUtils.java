package uk.gov.beis.els.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// This is a singleton rather than a Spring bean as it needs to be accessible from the unmanaged TemplatePopulator POJO
public class FontUtils {
  public static final FontUtils INSTANCE = new FontUtils();

  // Character widths are relative to font size in pixels. For example, if a character is 10px wide when the font
  // size is 10px, the character width is 1. If it's 7px wide when the font size is 10px, the character width is 0.7
  private final Map<String, Map<Character, Float>> fontCharMaps = new HashMap<>();
  private final TypeReference<HashMap<Character,Float>> charMapTypeRef = new TypeReference<HashMap<Character,Float>>() {};
  private final ObjectMapper objectMapper = new ObjectMapper();

  private FontUtils() {
    loadCharacterWidthMaps();
  }

  public float calculateEstimatedTextWidth(String text, float fontSize, String fontFaceName) {
    Map<Character, Float> charMap = getCharMap(fontFaceName);
    String processedText = processUnpairedSurrogateCodePoints(text);
    return fontSize * processedText.chars()
      .mapToObj(c -> (char) c)
      .reduce(0f, (subtotal, character) -> subtotal + charMap.getOrDefault(character, getFallbackCharWidth(charMap)), Float::sum);
  }

  /**
   * Unicode characters that can't fit into the 16-bit char datatype are represented as 'unpaired surrogates' of multiple char
   * types. E.g. the string "💡" (U+1F4A1) is represented as 2 chars (\uD83D and \uDCA1).
   *
   * This causes the width estimation logic to double count these single unicode codepoints, as they are represented
   * by multiple chars. This method removes the unpaired surrogates and replaces them with a single fallback char, making
   * the number of chars equal to the number of unicode codepoints.
   *
   * @param text The text to process
   * @return The text with unpaired surrogates removed and replaced with a single fallback char for each set of surrogates.
   */
  private String processUnpairedSurrogateCodePoints(String text) {
    int textLength = text.length();
    int codePointCount = text.codePointCount(0, textLength);
    if(textLength != codePointCount) { // The string contains symbols larger than 16-byte
      int extraCharCount = textLength - codePointCount;

      // Remove the codepoints represented as unpaired surrogates
      StringBuilder sb = new StringBuilder(text.replaceAll("[^\u0000-\uffff]", ""));

      // Append an equivalent number of fallback chars
      for (int i = 0; i < extraCharCount; i++) {
        sb.append(getFallbackChar());
      }
      return sb.toString();
    } else {
      return text;
    }
  }

  private void loadCharacterWidthMaps() {
    fontCharMaps.put("Verdana Regular", parseCharMap("verdana-regular.json"));
    fontCharMaps.put("Verdana Bold", parseCharMap("verdana-bold.json"));
    fontCharMaps.put("Calibri Regular", parseCharMap("calibri-regular.json"));
  }

  private float getFallbackCharWidth(Map<Character, Float> charMap) {
    return charMap.get(getFallbackChar());
  }

  private char getFallbackChar() {
    // Return 'W' as the widest standard character to use for width calculation when the specific char is not present
    return 'W';
  }

  private Map<Character, Float> getCharMap(String fontFaceName) {
    Map<Character, Float> charMap = fontCharMaps.get(fontFaceName);
    if(charMap == null) {
      throw new RuntimeException(String.format("Unknown font face name '%s' while looking up character width maps", fontFaceName));
    }
    return charMap;
  }

  private Map<Character, Float> parseCharMap(String fileName) {
    try {
      return objectMapper.readValue(new ClassPathResource("character-width-maps/" + fileName).getInputStream(), charMapTypeRef);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Failed to parse character width map file with name '%s'", fileName), e);
    }
  }
}