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
          "BOILER_SPACE_HEATERS",
          "Boiler space heaters",
          ReverseRouter.route(on(SpaceHeatersController.class).renderBoilerSpaceHeaters(null))))
      .add(new CategoryItem(
        "BOILER_COMBINATION_HEATERS",
        "Boiler combination heaters",
        ReverseRouter.route(on(SpaceHeatersController.class).renderBoilerCombinationHeaters(null))))
      .add(new CategoryItem(
          "COGENERATION_SPACE_HEATERS",
          "Cogeneration space heaters",
          ReverseRouter.route(on(SpaceHeatersController.class).renderCogenerationSpaceHeaters(null))))
      .add(new CategoryItem(
          "HEAT_PUMP_SPACE_HEATERS",
          "Heat pump space heaters (except low-temperature heat pumps)",
          ReverseRouter.route(on(SpaceHeatersController.class).renderHeatPumpSpaceHeaters(null))))
      .add(new CategoryItem(
          "HEAT_PUMP_COMBINATION_HEATERS",
          "Heat pump combination heaters",
          ReverseRouter.route(on(SpaceHeatersController.class).renderHeatPumpCombinationHeaters(null))))
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
  public String getCommonProductGuidanceText() {
    return "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. The size must be 105mm x 200mm on the product or 210mm x 297mm on packaging.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
