package uk.co.fivium.els.categories.spaceheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.spaceheaters.controller.SpaceHeatersController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class SpaceHeaterCategory implements Category {

  public static final Category GET = new SpaceHeaterCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "HEAT_PUMP_SPACE_HEATERS",
          "Heat pump space heaters (except low-temperature heat pumps)",
          ReverseRouter.route(on(SpaceHeatersController.class).renderHeatPumpSpaceHeaters(null))))
      .add(new CategoryItem(
          "LOW_TEMPERATURE_HEAT_PUMP_SPACE_HEATERS",
          "Low-temperature heat pump space heaters",
          ReverseRouter.route(on(SpaceHeatersController.class).renderLowTemperatureHeatPumpSpaceHeaters(null))))
      .build();

  private SpaceHeaterCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of space heater do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of space heater";
  }

  @Override
  public String getGuidanceText() {
    return "<p>To generate a label for a boiler space heater, cogeneration space heater, heat pump space heater, low-temperature heat pump, boiler combination heater, heat pump combination heater, or a package label associated with any of the above, select your label type below and enter the relevant information on the next page.</p>" +
        "<p>Energy labels for products should be 105mm x 200mm while package labels should be 210mm x 297mm. The label, for either the package, the product, or both as required, should be displayed at the point of sale so that it is easily readable and clearly associated with the product.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
