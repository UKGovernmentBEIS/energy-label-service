package uk.co.fivium.els.categories.tumbledryers.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.tumbledryers.controller.TumbleDryersController;
import uk.co.fivium.els.mvc.ReverseRouter;

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
  public String getGuidanceText() {
    return "<p>To generate a label for an air-vented household tumble dryer, a condenser tumble dryer or a gas-fired tumble dryer, select your label type below and enter the relevant information on the next page.</p>" +
        "<p>Energy labels for tumble dryers should be at least 110mm x 220mm when printed. The label should then be attached to the front or top of the product so that it is clearly visible.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
