package uk.gov.beis.els.categories.spaceheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.spaceheaters.controller.SpaceHeatersController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class SpaceHeaterPackagesCategory implements Category {

  public static final Category GET = new SpaceHeaterPackagesCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "PACKAGE_SPACE_HEATERS_CALCULATOR",
          "Yes, help me calculate the energy rating",
          ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesPreferentialHeaterSortQuestion(null))))
      .add(new CategoryItem(
          "PACKAGE_SPACE_HEATERS",
          "No, I already know the energy rating",
          ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackages(null))))
      .build();

  @Override
  public String getCategoryQuestionText() {
    return null;
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Specify if you need help calculating the energy rating.";
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
