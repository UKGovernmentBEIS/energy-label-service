package uk.co.fivium.els.categories.solidfuelboilers.model;

import com.google.common.collect.ImmutableList;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.solidfuelboilers.controller.SolidFuelBoilersController;
import uk.co.fivium.els.mvc.ReverseRouter;

import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

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
  public String getGuidanceText() {
    return "<p>To generate a label for a solid fuel boiler, or packages of a solid fuel boiler, supplementary heaters, temperature controls and solar devices select your label type below and enter the relevant information on the next page.</p>" +
        "<p>Energy labels for products should be 105mm x 200mm while package labels should be 210mm x 297mm. The label, for either the package, the product, or both as required, should be displayed at the point of sale so that it is easily readable and clearly associated with the product.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
