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
        "An original-style or new-style 'rescaled' label including the energy rating, weighted energy consumption, supplier's name and model identification code",
        ReverseRouter.route(on(LampsController.class).renderLamps(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL",
        "An original-style label including the energy rating and weighted energy consumption only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null))))
    .add(new CategoryItem(
        "LAMPS_EX_NAME_MODEL_CONSUMPTION",
        "An original-style label including the energy rating only",
        ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null))))
    .add(new CategoryItem(
        "LAMPS_PACKAGING_ARROW",
        "An arrow containing the energy rating for the front of the packaging, for products which use a new-style 'rescaled' label",
        ReverseRouter.route(on(LampsController.class).renderLampsPackagingArrow(null))))
    .build();

  private LampsCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What do you need to create?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select which type of label you need to create";
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
