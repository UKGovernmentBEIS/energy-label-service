package uk.co.fivium.els.categories.domesticovens.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.domesticovens.controller.DomesticOvensController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class DomesticOvenCategory implements Category {

  public static final Category GET = new DomesticOvenCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
    .add(new CategoryItem(
        "ELECTRIC_OVENS",
        "Electric ovens",
        ReverseRouter.route(on(DomesticOvensController.class).renderElectricOvens(null))))
    .add(new CategoryItem(
      "GAS_OVENS",
      "Gas ovens",
      ReverseRouter.route(on(DomesticOvensController.class).renderGasOvens(null))))
    .build();

  private DomesticOvenCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of domestic oven do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of domestic oven";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "Energy labels for domestic ovens should be at least 85mm x 170mm when printed. The label should then be attached to the front or top of the product so that it is clearly visible.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
