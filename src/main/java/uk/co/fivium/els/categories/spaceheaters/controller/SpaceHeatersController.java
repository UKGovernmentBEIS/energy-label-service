package uk.co.fivium.els.categories.spaceheaters.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.co.fivium.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.co.fivium.els.categories.spaceheaters.model.SpaceHeaterCategory;
import uk.co.fivium.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.co.fivium.els.categories.televisions.service.TelevisionsService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories/space-heaters")
public class SpaceHeatersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Space heaters";

  private final PdfRenderer pdfRenderer;
  private final SpaceHeatersService spaceHeatersService;
  private final BreadcrumbService breadcrumbService;


  @Autowired
  public SpaceHeatersController(PdfRenderer pdfRenderer, SpaceHeatersService spaceHeatersService, BreadcrumbService breadcrumbService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, SpaceHeaterCategory.GET, SpaceHeatersController.class);
    this.pdfRenderer = pdfRenderer;
    this.spaceHeatersService = spaceHeatersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/low-temperature-heat-pump-space-heaters")
  public ModelAndView renderLowTemperatureHeatPumpSpaceHeaters(@ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form) {
    return getLowTemperatureHeatPumpSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/low-temperature-heat-pump-space-heaters")
  @ResponseBody
  public Object handleLowTemperatureHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {

    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getLowTempEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(form.getLowTempEfficiencyRating(), category)) {
        bindingResult.rejectValue("lowTempEfficiencyRating", "lowTempEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }

    if (bindingResult.hasErrors()) {
      return getLowTemperatureHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      Resource pdf = pdfRenderer.render(spaceHeatersService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "space-heaters-label.pdf");
    }
  }

  @GetMapping("/heat-pump-space-heaters")
  public ModelAndView renderHeatPumpSpaceHeaters(@ModelAttribute("form") HeatPumpSpaceHeatersForm form) {
    return getHeatPumpSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-space-heaters")
  @ResponseBody
  public Object handleHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpSpaceHeatersForm form, BindingResult bindingResult) {

    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getLowTempEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(form.getLowTempEfficiencyRating(), category)) {
        bindingResult.rejectValue("lowTempEfficiencyRating", "lowTempEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }

    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getMediumTempEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(form.getMediumTempEfficiencyRating(), category)) {
        bindingResult.rejectValue("mediumTempEfficiencyRating", "mediumTempEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }

    if (bindingResult.hasErrors()) {
      return getHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      Resource pdf = pdfRenderer.render(spaceHeatersService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "space-heaters-label.pdf");
    }
  }

  private ModelAndView getLowTemperatureHeatPumpSpaceHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/lowTemperatureHeatPumpSpaceHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderLowTemperatureHeatPumpSpaceHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Low-temperature heat pump space heaters");
    return modelAndView;
  }

  private ModelAndView getHeatPumpSpaceHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/heatPumpSpaceHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderHeatPumpSpaceHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heat pump space heaters");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(SpaceHeatersService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(SpaceHeatersService.LEGISLATION_CATEGORIES));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        SpaceHeatersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
