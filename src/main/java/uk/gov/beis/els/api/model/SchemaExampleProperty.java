package uk.gov.beis.els.api.model;

public class SchemaExampleProperty {

  private String propertyName;
  private String exampleInput;

  public SchemaExampleProperty() {
  }

  public SchemaExampleProperty(String propertyName, String exampleInput) {
    this.propertyName = propertyName;
    this.exampleInput = exampleInput;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getExampleInput() {
    return exampleInput;
  }

  public void setExampleInput(String exampleInput) {
    this.exampleInput = exampleInput;
  }
}
