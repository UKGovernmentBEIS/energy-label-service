package uk.gov.beis.els.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.airconditioners.controller.AirConditionersController;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.dishwashers.controller.DishwashersController;
import uk.gov.beis.els.categories.domesticovens.controller.DomesticOvensController;
import uk.gov.beis.els.categories.lamps.controller.LampsController;
import uk.gov.beis.els.categories.localspaceheaters.controller.LocalSpaceHeatersController;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.controller.ProRefrigeratedCabinetsController;
import uk.gov.beis.els.categories.rangehoods.controller.RangeHoodsController;
import uk.gov.beis.els.categories.refrigeratingappliances.controller.RefrigeratingAppliancesController;
import uk.gov.beis.els.categories.solidfuelboilers.controller.SolidFuelBoilersController;
import uk.gov.beis.els.categories.spaceheaters.controller.SpaceHeatersController;
import uk.gov.beis.els.categories.televisions.controller.TelevisionController;
import uk.gov.beis.els.categories.tumbledryers.controller.TumbleDryersController;
import uk.gov.beis.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.gov.beis.els.categories.washingmachines.controller.WashingMachinesController;
import uk.gov.beis.els.categories.waterheaters.controller.WaterHeatersController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class ProductCategory implements Category {

  public static final Category GET = new ProductCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "AIR_CONDITIONERS",
          "Air conditioners",
          ReverseRouter.route(on(AirConditionersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "DISHWASHERS",
          "Dishwashers",
          ReverseRouter.route(on(DishwashersController.class).renderDishwashers(null))))
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
          ReverseRouter.route(on(LocalSpaceHeatersController.class).renderLocalSpaceHeaters(null))))
      .add(new CategoryItem(
          "DOMESTIC_OVENS",
          "Ovens",
          ReverseRouter.route(on(DomesticOvensController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "RANGE_HOODS",
          "Range hoods",
          ReverseRouter.route(on(RangeHoodsController.class).renderRangeHoodsForm(null))))
      .add(new CategoryItem(
          "REFRIGERATED_STORAGE_CABINETS",
          "Refrigerated storage cabinets for professional use",
          ReverseRouter.route(on(ProRefrigeratedCabinetsController.class).renderProfessionalRefrigeratedStorageCabinetsForm(null))))
      .add(new CategoryItem(
          "SOLID_FUEL_BOILERS",
          "Solid fuel boilers",
          ReverseRouter.route(on(SolidFuelBoilersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "SPACE_HEATERS",
          "Space heaters",
          ReverseRouter.route(on(SpaceHeatersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
      .add(new CategoryItem(
          "TELEVISIONS",
          "Televisions",
          ReverseRouter.route(on(TelevisionController.class).renderTelevisionsForm(null))))
      .add(new CategoryItem(
          "TUMBLE_DRYERS",
          "Tumble dryers",
          ReverseRouter.route(on(TumbleDryersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult()))))
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
  public String getCommonProductGuidanceText() {
    return null;
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
