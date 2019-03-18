package uk.co.fivium.els.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.dishwashers.controller.DishwashersController;
import uk.co.fivium.els.categories.refrigeratingappliances.controller.RefrigeratingAppliancesController;
import uk.co.fivium.els.categories.lamps.controller.LampsController;
import uk.co.fivium.els.categories.televisions.controller.TelevisionController;
import uk.co.fivium.els.categories.tumbledryers.controller.TumbleDryersController;
import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.categories.washingmachines.controller.WashingMachinesController;
import uk.co.fivium.els.categories.waterheaters.controller.WaterHeatersController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class ProductCategory implements Category {

  public static final Category GET = new ProductCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "AIR_CONDITIONERS",
          "Air Conditioners",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "DISHWASHERS",
          "Dishwashers",
          ReverseRouter.route(on(DishwashersController.class).renderDishwashers(null))))
      .add(new CategoryItem(
          "DOMESTIC_OVENS",
          "Domestic ovens",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "FRIDGES_AND_FREEZERS",
          "Fridges and freezers",
          ReverseRouter.route(on(RefrigeratingAppliancesController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "LAMPS",
          "Lamps",
          ReverseRouter.route(on(LampsController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "LOCAL_SPACE_HEATERS",
          "Local space heaters",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "REFRIGERATED_STORAGE_CABINETS",
          "Professional refrigerated storage cabinets",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "RANGE_HOODS",
          "Range hoods",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "SOLID_FUEL_BOILERS",
          "Solid fuel boilers",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "SPACE_HEATERS",
          "Space heaters",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "TELEVISIONS",
          "Televisions",
          ReverseRouter.route(on(TelevisionController.class).renderTelevisionsForm(null))))
      .add(new CategoryItem(
          "TUMBLE_DRYERS",
          "Tumble dryers",
          ReverseRouter.route(on(TumbleDryersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "TYRES",
          "Tyres",
          "/not-yet-implemented"))
      .add(new CategoryItem(
          "VENTILATION_UNITS",
          "Ventilation units",
          ReverseRouter.route(on(VentilationUnitsController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "WASHING_MACHINES",
          "Washing machines",
          ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null))))
      .add(new CategoryItem(
          "WATER_HEATERS",
          "Water heaters",
          ReverseRouter.route(on(WaterHeatersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .build();

  private ProductCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of item do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of item";
  }

  @Override
  public String getGuidanceText() {
    return null;
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
