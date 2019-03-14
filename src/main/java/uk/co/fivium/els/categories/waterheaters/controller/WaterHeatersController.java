package uk.co.fivium.els.categories.waterheaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.co.fivium.els.categories.waterheaters.model.LoadProfile;
import uk.co.fivium.els.categories.waterheaters.model.WaterHeaterSubCategory;
import uk.co.fivium.els.categories.waterheaters.model.WaterHeaterSubCategoryForm;
import uk.co.fivium.els.categories.waterheaters.service.WaterHeatersService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories/water-heaters")
public class WaterHeatersController {

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
  public ModelAndView renderWaterHeatersSubCategories(@ModelAttribute("form") WaterHeaterSubCategoryForm form) {
    return getSubCategory();
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleWaterHeatersSubCategoriesSubmit(@Valid @ModelAttribute("form") WaterHeaterSubCategoryForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSubCategory();
    }
    else {
      WaterHeaterSubCategory subCategory = WaterHeaterSubCategory.valueOf(form.getSubCategory());
      return new ModelAndView("redirect:" + subCategory.getNextStateUrl());
    }
  }

  private ModelAndView getSubCategory() {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/waterHeatersSubCategory");
    modelAndView.addObject("subCategories",
      Arrays.stream(WaterHeaterSubCategory.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, WaterHeaterSubCategory::getDisplayName))
    );
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(WaterHeatersController.class).handleWaterHeatersSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Water heaters and storage tanks", ReverseRouter.route(on(WaterHeatersController.class).renderWaterHeatersSubCategories(null)));
    return modelAndView;
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
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
      Arrays.stream(LoadProfile.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Water heaters and storage tanks", ReverseRouter.route(on(WaterHeatersController.class).renderWaterHeatersSubCategories(null)));
  }
}
