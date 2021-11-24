package uk.gov.beis.els.model.meta;

/**
 * As of Spring Boot 2.1.14, properties on annotation classes seemingly can't be accessed from with freemarker templates.
 * Suspect this is due to changes in https://issues.apache.org/jira/browse/FREEMARKER-124 which was implemented in
 * https://freemarker.apache.org/docs/versions_2_3_30.html which was included in the 2.1.14 Spring Boot release
 * https://github.com/spring-projects/spring-boot/releases/tag/v2.1.14.RELEASE
 *
 * So we pass this POJO to the model, instead of directly passing the FieldPrompt
 */
public class ResolvedFieldPrompt {
  private final String promptText;
  private final String hintText;

  public ResolvedFieldPrompt(String promptText, String hintText) {
    this.promptText = promptText;
    this.hintText = hintText;
  }

  public String getPromptText() {
    return promptText;
  }

  public String getHintText() {
    return hintText;
  }
}
