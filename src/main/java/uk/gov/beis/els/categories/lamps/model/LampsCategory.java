package uk.gov.beis.els.categories.lamps.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.lamps.controller.LampsController;
import uk.gov.beis.els.mvc.ReverseRouter;

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

  @Override
  public String getCommonProductGuidanceText() {
    return null;
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
