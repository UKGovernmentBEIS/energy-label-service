package uk.co.fivium.els.categories.solidfuelboilers.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilerCategory;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilerPackagesForm;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilersForm;
import uk.co.fivium.els.categories.solidfuelboilers.service.SolidFuelBoilersService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.ResponseService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/solid-fuel-boilers")
public class SolidFuelBoilersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Solid fuel boilers and packages";

  private final SolidFuelBoilersService solidFuelBoilersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final ResponseService responseService;

  @Autowired
  public SolidFuelBoilersController(SolidFuelBoilersService solidFuelBoilersService,
                                    BreadcrumbService breadcrumbService,
                                    InternetLabelService internetLabelService,
                                    ResponseService responseService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, SolidFuelBoilerCategory.GET, SolidFuelBoilersController.class);
    this.solidFuelBoilersService = solidFuelBoilersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.responseService = responseService;
  }

  @GetMapping("/solid-fuel-boilers")
  public ModelAndView renderSolidFuelBoilers(@ModelAttribute("form") SolidFuelBoilersForm form) {
    return getSolidFuelBoilers(Collections.emptyList());
  }

  @PostMapping("/solid-fuel-boilers")
  @ResponseBody
  public Object handleSolidFuelBoilersSubmit(@Valid @ModelAttribute("form") SolidFuelBoilersForm form, BindingResult bindingResult) {
    return doIfValidSolidFuelBoiler(form, bindingResult, (category -> responseService.processPdfResponse(solidFuelBoilersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/solid-fuel-boilers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelSolidFuelBoilersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") SolidFuelBoilersForm form, BindingResult bindingResult) {
    return doIfValidSolidFuelBoiler(form, bindingResult, (category -> internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, "solid-fuel-boilers")));
  }

  private Object doIfValidSolidFuelBoiler(SolidFuelBoilersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS, bindingResult);

    if (bindingResult.hasErrors()) {
      return getSolidFuelBoilers(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SolidFuelBoilersService.LEGISLATION_CATEGORIES_BOILERS);
      return function.apply(category);
    }
  }

  @GetMapping("/package-solid-fuel-boiler")
  public ModelAndView renderSolidFuelBoilerPackages(@ModelAttribute("form") SolidFuelBoilerPackagesForm form) {
    return getSolidFuelBoilerPackages(Collections.emptyList());
  }

  @PostMapping("/package-solid-fuel-boiler")
  @ResponseBody
  public Object handleSolidFuelBoilerPackagesSubmit(@Valid @ModelAttribute("form") SolidFuelBoilerPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolidFuelBoilerPackages(bindingResult.getFieldErrors());
    }
    else {
      return responseService.processPdfResponse(solidFuelBoilersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/package-solid-fuel-boiler", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelSolidFuelBoilerPackagesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") SolidFuelBoilerPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolidFuelBoilerPackages(bindingResult.getFieldErrors());
    }
    else {
      return responseService.processImageResponse(internetLabelService.generateInternetLabelHtml(form, form.getPackageEfficiencyRating(), SolidFuelBoilersService.LEGISLATION_CATEGORY_PACKAGES_CURRENT, ProductMetadata.SOLID_FUEL_BOILER_PACKAGE));
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
    breadcrumbService.pushLastBreadcrumb(modelAndView,"Package solid fuel boilers");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(SolidFuelBoilersController.class).renderCategories(null)));
  }
}
