package uk.gov.beis.els.model;

import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;

public enum InternetLabelTemplate {
  ORIGINAL(
          "internet-labelling-left.svg",
          "internet-labelling-right.svg",
          null,
          null,
          false
  ),
  RESCALED(
          "internet-labelling-rescaled-left.svg",
          "internet-labelling-rescaled-right.svg",
          "internet-labelling-rescaled-bw-left.svg",
          "internet-labelling-rescaled-bw-right.svg",
          true
  );

  private final String leftColourTemplatePath;
  private final String rightColourTemplatePath;
  private final String leftBWTemplatePath;
  private final String rightBWTemplatePath;
  private final Boolean hasBWOption;

  InternetLabelTemplate(String leftColourTemplatePath, String rightColourTemplatePath, String leftBWTemplatePath, String rightBWTemplatePath, Boolean hasBWOption) {
    this.leftColourTemplatePath = leftColourTemplatePath;
    this.rightColourTemplatePath = rightColourTemplatePath;
    this.leftBWTemplatePath = leftBWTemplatePath;
    this.rightBWTemplatePath = rightBWTemplatePath;
    this.hasBWOption = hasBWOption;
  }

  public String getTemplatePathForOrientationAndColour(InternetLabelOrientation orientation, InternetLabelColour colour) {
    String templatePath = null;

    if(orientation == InternetLabelOrientation.LEFT) {
      if(colour == InternetLabelColour.COLOUR) {
        templatePath = this.leftColourTemplatePath;
      } else if(colour == InternetLabelColour.BW) {
        templatePath = this.leftBWTemplatePath;
      }
    } else if(orientation == InternetLabelOrientation.RIGHT) {
      if(colour == InternetLabelColour.COLOUR) {
        templatePath = this.rightColourTemplatePath;
      } else if(colour == InternetLabelColour.BW) {
        templatePath = this.rightBWTemplatePath;
      }
    }

    if(templatePath == null) {
      throw new RuntimeException(String.format("Internet label template path not found for orientation %s, colour %s", orientation, colour));
    } else {
      return templatePath;
    }
  }

  public String getLeftColourTemplatePath() {
    return leftColourTemplatePath;
  }

  public String getRightColourTemplatePath() {
    return rightColourTemplatePath;
  }

  public String getLeftBWTemplatePath() {
    return leftBWTemplatePath;
  }

  public String getRightBWTemplatePath() {
    return rightBWTemplatePath;
  }

  public Boolean getHasBWOption() {
    return hasBWOption;
  }
}
