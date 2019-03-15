package uk.co.fivium.els.categories.refrigeratingappliances.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.refrigeratingappliances.controller.RefrigeratingAppliancesController;
import uk.co.fivium.els.mvc.ReverseRouter;

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
  public String getGuidanceText() {
    return "<p>To generate an energy label for a domestic fridge, freezer or wine cooler, select your product type below and enter the product information on the next page.</p>" +
        "<p>Energy labels for domestic fridges, freezers or wine coolers should be at least 110mm x 220mm when printed. The label should then be displayed so that it is easily readable and clearly associated with the product.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
