package uk.gov.beis.els.categories.washingmachines.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.washingmachines.controller.WashingMachinesController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class WashingMachinesCategory implements Category {

  public static final Category GET = new WashingMachinesCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "WASHING_MACHINES",
          "Washing machines",
          ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null))))
      .add(new CategoryItem(
          "WASHER_DRYERS",
          "Washer-dryers",
          ReverseRouter.route(on(WashingMachinesController.class).renderWasherDryer(null))))
      .build();

  private WashingMachinesCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of washing machine do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of washing machine";
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