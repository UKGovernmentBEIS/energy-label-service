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
        "Label with supplier's name/trademark, model identifier, energy rating and weighted energy consumption",
        ReverseRouter.route(on(LampsController.class).renderLamps(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL",
        "Label with energy rating and weighted energy consumption only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL_CONSUMPTION",
        "Label with energy rating only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null))))
    .build();

  private LampsCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of label do you need for this lamp?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of label";
  }

  // Lamps are a special case where the internet label link should be on the subcategory guidance page since the subcategories are not separate products,
  // variants of the same 'type' of product
  @Override
  public String getCommonProductGuidanceText() {
    return "<p>To generate an energy label for a lamp, select what you want on the label below and enter the product information on the next page.</p>" +
        "<p>Energy labels for lamps should be at least 36mm x 76mm when printed, unless the packaging is not large enough to accommodate this. This label can be displayed either in colour or in black and white.</p>" +
        "<p>You can also <a class=\"govuk-link\" href=\"lamps?mode=INTERNET\">generate a nested arrow</a> for products sold via the internet.</p>";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
