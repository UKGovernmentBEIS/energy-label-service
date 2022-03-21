package uk.gov.beis.els.categories.spaceheaters.model;

public enum TemperatureControlClass {
  I(1),
  II(2),
  III(1.5F),
  IV(2),
  V(3),
  VI(4),
  VII(3.5F),
  VIII(5);

  private final float classValue;

  TemperatureControlClass(float classValue) {
    this.classValue = classValue;
  }

  public float getClassValue() {
    return classValue;
  }
}
