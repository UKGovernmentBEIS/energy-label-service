package uk.co.fivium.els.categories.waterheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public class WaterHeaterCategory implements Category {

  public static final Category GET = new WaterHeaterCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "HEAT_PUMP_WATER_HEATERS",
          "Heat pump water heaters",
          ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))))
      .build();

  private WaterHeaterCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of water heater or storage tank do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of water heater or storage tank";
  }

  @Override
  public String getGuidanceText() {
    return "TODO";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }
}
