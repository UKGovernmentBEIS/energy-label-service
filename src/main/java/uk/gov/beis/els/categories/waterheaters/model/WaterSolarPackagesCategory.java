package uk.gov.beis.els.categories.waterheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.waterheaters.controller.WaterHeatersController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class WaterSolarPackagesCategory implements Category {

  public static final Category GET = new WaterSolarPackagesCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "WATER_SOLAR_PACKAGES_CALCULATOR",
          "Yes, help me calculate the energy rating",
          ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackagesCalculator(null))))
      .add(new CategoryItem(
          "WATER_SOLAR_PACKAGES",
          "No, I already know the energy rating",
          ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackages(null))))
      .build();

  @Override
  public String getCategoryQuestionText() {
    return null;
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select if you know the values to calculate or if need help of the calculator";
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
