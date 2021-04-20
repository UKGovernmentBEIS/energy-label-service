package uk.gov.beis.els.categories.refrigeratingappliances.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.refrigeratingappliances.controller.RefrigeratingAppliancesController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class RefrigeratingAppliancesCategory implements Category {

  public static final Category GET = new RefrigeratingAppliancesCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "FRIDGES_FREEZERS",
          "Household fridges and freezers",
          ReverseRouter.route(on(RefrigeratingAppliancesController.class).renderFridgesFreezers(null))))
      .add(new CategoryItem(
          "WINE_STORAGE_APPLIANCES",
          "Wine storage appliances",
          ReverseRouter.route(on(RefrigeratingAppliancesController.class).renderWineStorageAppliances(null))))
      .build();

  private RefrigeratingAppliancesCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of household refrigerating appliance do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of household refrigerating appliance";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in appliance it doesn't have to be attached to the product, but it must still be easy to see. Old-style labels must be at least 110mm x 220mm when printed. New-style rescaled labels must be at least 96mm x 192mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
