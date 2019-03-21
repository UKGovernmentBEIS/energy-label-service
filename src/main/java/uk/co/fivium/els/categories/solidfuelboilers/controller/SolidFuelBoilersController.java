package uk.co.fivium.els.categories.solidfuelboilers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilerCategory;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilerPackagesForm;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilersForm;
import uk.co.fivium.els.categories.solidfuelboilers.service.SolidFuelBoilersService;
import uk.co.fivium.els.controller.CategoryController;
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
@RequestMapping("/categories/solid-fuel-boilers")
public class SolidFuelBoilersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Solid fuel boilers and packages";

  private final PdfRenderer pdfRenderer;
  private final SolidFuelBoilersService solidFuelBoilersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public SolidFuelBoilersController(PdfRenderer pdfRenderer, SolidFuelBoilersService solidFuelBoilersService, BreadcrumbService breadcrumbService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, SolidFuelBoilerCategory.GET, SolidFuelBoilersController.class);
    this.pdfRenderer = pdfRenderer;
    this.solidFuelBoilersService = solidFuelBoilersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/solid-fuel-boilers")
  public ModelAndView renderSolidFuelBoilers(@ModelAttribute("form") SolidFuelBoilersForm form) {
    return getSolidFuelBoilers(Collections.emptyList());
  }

  @PostMapping("/solid-fuel-boilers")
  @ResponseBody
  public Object handleSolidFuelBoilersSubmit(@Valid @ModelAttribute("form") SolidFuelBoilersForm form, BindingResult bindingResult) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS, bindingResult);

    if (bindingResult.hasErrors()) {
      return getSolidFuelBoilers(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS);
      Resource pdf = pdfRenderer.render(solidFuelBoilersService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "solid-fuel-boilers-label.pdf");
    }
  }

  @GetMapping("/packages-of-a-solid-fuel-boiler-supplementary-heaters-temperature-controls-and-solar-devices")
  public ModelAndView renderSolidFuelBoilerPackages(@ModelAttribute("form") SolidFuelBoilerPackagesForm form) {
    return getSolidFuelBoilerPackages(Collections.emptyList());
  }

  @PostMapping("/packages-of-a-solid-fuel-boiler-supplementary-heaters-temperature-controls-and-solar-devices")
  @ResponseBody
  public Object handleSolidFuelBoilerPackagesSubmit(@Valid @ModelAttribute("form") SolidFuelBoilerPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolidFuelBoilerPackages(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(solidFuelBoilersService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "solid-fuel-boilers-label.pdf");
    }
  }

  private ModelAndView getSolidFuelBoilers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/solid-fuel-boilers/solidFuelBoilers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SolidFuelBoilersController.class).renderSolidFuelBoilers(null)));
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS));
    breadcrumbService.pushLastBreadcrumb(modelAndView,"Solid fuel boilers");
    return modelAndView;
  }

  private ModelAndView getSolidFuelBoilerPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/solid-fuel-boilers/solidFuelBoilerPackages");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SolidFuelBoilersController.class).renderSolidFuelBoilerPackages(null)));
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(SolidFuelBoilersService.LEGISLATION_CATEGORY_PACKAGES_CURRENT.getPrimaryRatingRange()));
    breadcrumbService.pushLastBreadcrumb(modelAndView,"Packages of a solid fuel boiler, supplementary heaters, temperature controls and solar devices");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(SolidFuelBoilersController.class).renderCategories(null)));
  }
}
