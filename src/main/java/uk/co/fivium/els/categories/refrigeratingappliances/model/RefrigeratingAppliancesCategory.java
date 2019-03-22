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
  public String getCommonProductGuidanceText() {
    return "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 110mm x 220mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
