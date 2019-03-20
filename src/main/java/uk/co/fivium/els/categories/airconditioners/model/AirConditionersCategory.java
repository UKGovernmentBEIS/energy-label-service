package uk.co.fivium.els.categories.airconditioners.model;

import com.google.common.collect.ImmutableList;
import uk.co.fivium.els.categories.airconditioners.controller.AirConditionersController;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.mvc.ReverseRouter;

import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class AirConditionersCategory implements Category {

  public static final Category GET = new AirConditionersCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
    .add(new CategoryItem(
      "COOLING_DUCTLESS_AIR_CONDITIONERS",
      "Cooling-only ductless air conditioners",
      ReverseRouter.route(on(AirConditionersController.class).renderCoolingDuctlessAirConditioners(null))))
    .add(new CategoryItem(
        "REVERSIBLE_DUCTLESS_AIR_CONDITIONERS",
        "Reversible ductless air conditioners",
        ReverseRouter.route(on(AirConditionersController.class).renderReversibleDuctlessAirConditioners(null))))
    .build();

  private AirConditionersCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of air conditioner do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of air conditioner";
  }

  @Override
  public String getGuidanceText() {
    return "<p>Lorem ipsum.</p>" +
        "<p>Dolor sit amet.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
