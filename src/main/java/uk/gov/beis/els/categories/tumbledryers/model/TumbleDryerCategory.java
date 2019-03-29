package uk.gov.beis.els.categories.tumbledryers.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.tumbledryers.controller.TumbleDryersController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class TumbleDryerCategory implements Category {

  public static final Category GET = new TumbleDryerCategory();

  private static final List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
    .add(new CategoryItem(
        "AIR_VENTED_TUMBLE_DRYERS",
        "Air vented tumble dryers",
        ReverseRouter.route(on(TumbleDryersController.class).renderAirVentedTumbleDryers(null))))
    .add(new CategoryItem(
        "CONDENSER_TUMBLE_DRYERS",
        "Condenser tumble dryers",
        ReverseRouter.route(on(TumbleDryersController.class).renderCondenserTumbleDryers(null))))
    .add(new CategoryItem(
        "GAS_FIRED_TUMBLE_DRYERS",
        "Gas-fired tumble dryers",
        ReverseRouter.route(on(TumbleDryersController.class).renderGasFiredTumbleDryers(null))))
    .build();

  private TumbleDryerCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of tumble dryer do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of tumble dryer";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
