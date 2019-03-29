package uk.gov.beis.els.categories.domesticovens.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.domesticovens.controller.DomesticOvensController;
import uk.gov.beis.els.mvc.ReverseRouter;

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
    return "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 85mm x 170mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
