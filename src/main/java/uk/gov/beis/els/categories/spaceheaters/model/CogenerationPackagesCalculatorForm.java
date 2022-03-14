package uk.gov.beis.els.categories.spaceheaters.model;

public class CogenerationPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.COGENERATION_HEATER;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }
}
