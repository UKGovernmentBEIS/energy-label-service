package uk.co.fivium.els.categories.ventilationunits.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class VentilationUnitCategory implements Category {
  // TODO these could be beans defined in a configuration class
  public static final Category GET = new VentilationUnitCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "UNIDIRECTIONAL_VENTILATION_UNITS",
          "Unidirectional ventilation units",
          ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))))
      .add(new CategoryItem(
          "BIDIRECTIONAL_VENTILATION_UNITS",
          "Bidirectional ventilation units",
          ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))))
      .build();

  private VentilationUnitCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of ventilation unit do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of ventilation unit";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "Energy labels for ventilation unites should be at least 75mm x 150mm when printed. This label should then be displayed so that it is easily readable and clearly associated with the product.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
