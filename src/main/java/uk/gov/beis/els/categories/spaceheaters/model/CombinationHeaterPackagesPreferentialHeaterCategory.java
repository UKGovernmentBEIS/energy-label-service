package uk.gov.beis.els.categories.spaceheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.spaceheaters.controller.SpaceHeatersController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class CombinationHeaterPackagesPreferentialHeaterCategory implements Category {

  public static final Category GET = new CombinationHeaterPackagesPreferentialHeaterCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "COMBINATION_SPACE_HEATERS_BOILER",
          "Boiler",
          ReverseRouter.route(on(SpaceHeatersController.class).renderCombinationHeaterPackagesBoilerCalculator(null))))
      .add(new CategoryItem(
          "COMBINATION_SPACE_HEATERS_HEAT_PUMP",
          "Heat pump",
          ReverseRouter.route(on(SpaceHeatersController.class).renderCombinationHeaterPackagesHeatPumpCalculator(null))))
      .build();

  @Override
  public String getCategoryQuestionText() {
    return null;
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select the package's preferential heater.";
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
