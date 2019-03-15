package uk.co.fivium.els.categories.waterheaters.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.co.fivium.els.categories.waterheaters.model.LoadProfile;
import uk.co.fivium.els.categories.waterheaters.model.WaterHeaterCategory;
import uk.co.fivium.els.categories.waterheaters.service.WaterHeatersService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/water-heaters")
public class WaterHeatersController {

  private final String BREADCRUMB_STAGE_TEXT = "Water heaters and storage tanks";

  private final PdfRenderer pdfRenderer;
  private final WaterHeatersService waterHeatersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public WaterHeatersController(PdfRenderer pdfRenderer, WaterHeatersService waterHeatersService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.waterHeatersService = waterHeatersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderWaterHeatersSubCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getVentilationUnitsSubCategory(Collections.emptyList());
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleWaterHeatersSubCategoriesSubmit(@Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    return ControllerUtils.handleSubCategorySubmit(WaterHeaterCategory.GET, form, bindingResult, (this::getVentilationUnitsSubCategory));
  }

  private ModelAndView getVentilationUnitsSubCategory(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(WaterHeaterCategory.GET,
        errors,
        ReverseRouter.route(on(WaterHeatersController.class).handleWaterHeatersSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
        BREADCRUMB_STAGE_TEXT,
        breadcrumbService
    );
  }

  @GetMapping("/heat-pump-water-heaters")
  public ModelAndView renderHeatPumpWaterHeaters(@ModelAttribute("form") HeatPumpWaterHeatersForm form) {
    return getHeatPumpWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-water-heaters")
  @ResponseBody
  public Object handleHeatPumpWaterHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpWaterHeatersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getHeatPumpWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  private ModelAndView getHeatPumpWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/heatPumpWaterHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderHeatPumpWaterHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heat pump water heaters");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = WaterHeatersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
      Arrays.stream(LoadProfile.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(WaterHeatersController.class).renderWaterHeatersSubCategories(null)));
  }
}
