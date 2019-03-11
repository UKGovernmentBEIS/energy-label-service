package uk.co.fivium.els.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TvForm {

  @NotBlank
  private String renderType;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 15, message="Name must not exceed 15 characters")
  private String manufacturer;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 15, message="Model must not exceed 15 characters")
  private String modelName;

  @NotBlank(message = "Field must not be blank")
  private String rating;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 3, min = 3, message="On-mode power consumption must be 3 digits")
  private String powerConsumption;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 3, min = 3, message="Annual on-mode energy consumption consumption must be 3 digits")
  private String annualPowerConsumption;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 3, min = 1, message="Screen size (cm) must be between 1 and 3 digits")
  private String screenSizeCm;

  @NotBlank(message = "Field must not be blank")
  @Size(max = 3, min = 1,message="Screen size (in) must be between 1 and 3 digits")
  private String screenSizeIn;

  public String getRenderType() {
    return renderType;
  }

  public void setRenderType(String renderType) {
    this.renderType = renderType;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getPowerConsumption() {
    return powerConsumption;
  }

  public void setPowerConsumption(String powerConsumption) {
    this.powerConsumption = powerConsumption;
  }

  public String getAnnualPowerConsumption() {
    return annualPowerConsumption;
  }

  public void setAnnualPowerConsumption(String annualPowerConsumption) {
    this.annualPowerConsumption = annualPowerConsumption;
  }

  public String getScreenSizeCm() {
    return screenSizeCm;
  }

  public void setScreenSizeCm(String screenSizeCm) {
    this.screenSizeCm = screenSizeCm;
  }

  public String getScreenSizeIn() {
    return screenSizeIn;
  }

  public void setScreenSizeIn(String screenSizeIn) {
    this.screenSizeIn = screenSizeIn;
  }
}
