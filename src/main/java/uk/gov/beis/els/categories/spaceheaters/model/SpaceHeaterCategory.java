package uk.gov.beis.els.categories.spaceheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.spaceheaters.controller.SpaceHeatersController;
import uk.gov.beis.els.mvc.ReverseRouter;

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
      .add(new CategoryItem(
          "PACKAGE_SPACE_HEATERS",
          "Packages of space heater, temperature control and solar device",
          ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesSortQuestion(null))))
      .add(new CategoryItem(
        "PACKAGE_COMBINATION_HEATERS",
        "Packages of combination heater, temperature control and solar device",
        ReverseRouter.route(on(SpaceHeatersController.class).renderCombinationHeaterPackages(null))))
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
    return null;
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
