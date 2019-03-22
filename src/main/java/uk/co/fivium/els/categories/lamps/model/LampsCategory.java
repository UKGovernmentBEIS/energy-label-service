package uk.co.fivium.els.categories.lamps.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.lamps.controller.LampsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class LampsCategory implements Category {

  // TODO these could be beans defined in a configuration class
  public static final Category GET = new LampsCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
    .add(new CategoryItem(
        "LAMPS",
        "The energy rating, weighted energy consumption, the supplier's name and model identification code",
        ReverseRouter.route(on(LampsController.class).renderLamps(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL",
        "The energy rating and weighted energy consumption only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL_CONSUMPTION",
        "The energy rating only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null))))
    .build();

  private LampsCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What information should the label include about the lamp?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select the information which should be included on the label";
  }

  // Lamps are a special case where the internet label link should also on the subcategory guidance page since the subcategories are not separate products,
  // they are variants of the same 'type' of product
  // TODO decide if this is needed or not
//  @Override
//  public String getCategoryPageGuidanceText() {
//    return "If the product will be shown on a website, you must also <a class=\"govuk-link\" href=\"lamps?mode=INTERNET\">get an online version of the label</a>";
//  }

  @Override
  public String getCommonProductGuidanceText() {
    return "The label should usually be at least 36mm x 76mm when attached to packaging, but you can make it smaller if it doesn't fit. It can be full colour or black and white.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
