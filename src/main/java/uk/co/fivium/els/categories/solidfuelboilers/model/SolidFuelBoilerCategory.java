package uk.co.fivium.els.categories.solidfuelboilers.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.solidfuelboilers.controller.SolidFuelBoilersController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class SolidFuelBoilerCategory implements Category {

  public static final Category GET = new SolidFuelBoilerCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
        "SOLID_FUEL_BOILERS",
        "Solid fuel boilers",
        ReverseRouter.route(on(SolidFuelBoilersController.class).renderSolidFuelBoilers(null))))
    .add(new CategoryItem(
      "SOLID_FUEL_BOILER_PACKAGES",
      "Packages of a solid fuel boiler, supplementary heaters, temperature controls and solar devices",
      ReverseRouter.route(on(SolidFuelBoilersController.class).renderSolidFuelBoilerPackages(null))))
      .build();

  private SolidFuelBoilerCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of solid fuel boiler or package do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of solid fuel boiler or package";
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
