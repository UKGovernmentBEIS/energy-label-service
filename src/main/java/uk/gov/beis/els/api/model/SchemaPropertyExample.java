package uk.gov.beis.els.api.model;

public class SchemaPropertyExample {

  private final String propertyName;
  private final String exampleInput;

  public SchemaPropertyExample(String propertyName, String exampleInput) {
    this.propertyName = propertyName;
    this.exampleInput = exampleInput;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public String getExampleInput() {
    return exampleInput;
  }
}
