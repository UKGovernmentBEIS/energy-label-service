package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.controller.RefrigeratorsDirectSalesController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class RefrigeratorsDirectSalesCategory implements Category {

  public static final Category GET = new RefrigeratorsDirectSalesCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
          .add(new CategoryItem(
                  "VENDING_MACHINES",
                  "Refrigerated vending machines",
                  ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderVendingMachines(null))))
          .add(new CategoryItem(
                  "ICE_CREAM_FREEZERS",
                  "Ice cream freezers",
                  ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderIceCreamFreezers(null))))
          .add(new CategoryItem(
                  "BEVERAGE_COOLERS",
                  "Beverage coolers",
                  ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderBeverageCoolers(null))))
          .add(new CategoryItem(
                  "REFRIGERATED_DISPLAY_CABINETS",
                  "Supermarket refrigerator/freezer cabinets or gelato-scooping cabinets",
                  ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderDisplayCabinets(null))))
          .build();

  private RefrigeratorsDirectSalesCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of direct sales refrigerator do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of refrigerator";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in appliance it doesn't have to be attached to the product, but it must still be easy to see. It must be at least 96mm x 192mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
