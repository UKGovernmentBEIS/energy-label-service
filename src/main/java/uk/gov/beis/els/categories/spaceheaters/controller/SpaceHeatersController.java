package uk.gov.beis.els.categories.spaceheaters.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
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
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CombinationHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterCategory;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;
import uk.gov.beis.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/space-heaters")
public class SpaceHeatersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Space heaters";

  private final SpaceHeatersService spaceHeatersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public SpaceHeatersController(SpaceHeatersService spaceHeatersService,
                                BreadcrumbService breadcrumbService,
                                InternetLabelService internetLabelService,
                                DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, SpaceHeaterCategory.GET, SpaceHeatersController.class);
    this.spaceHeatersService = spaceHeatersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/boiler-space-heaters")
  public ModelAndView renderBoilerSpaceHeaters(@ModelAttribute("form") BoilerSpaceHeatersForm form) {
    return getBoilerSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/boiler-space-heaters")
  @ResponseBody
  public Object handleBoilerSpaceHeatersSubmit(@Valid @ModelAttribute("form") BoilerSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidBoiler(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/boiler-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBoilerSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") BoilerSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidBoiler(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_BOILER))));
  }

  private Object doIfValidBoiler(BoilerSpaceHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    commonBoilerCogenValidation(form.getApplicableLegislation(), form.getEfficiencyRating(), bindingResult);
    if (bindingResult.hasErrors()) {
      return getBoilerSpaceHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  @GetMapping("/boiler-combination-heaters")
  public ModelAndView renderBoilerCombinationHeaters(@ModelAttribute("form") BoilerCombinationHeatersForm form) {
    return getBoilerCombinationHeaters(Collections.emptyList());
  }

  @PostMapping("/boiler-combination-heaters")
  @ResponseBody
  public Object handleBoilerCombinationHeatersSubmit(@Valid @ModelAttribute("form") BoilerCombinationHeatersForm form, BindingResult bindingResult) {
    return doIfValidBoilerCombi(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/boiler-combination-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBoilerCombinationHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") BoilerCombinationHeatersForm form, BindingResult bindingResult) {
    return doIfValidBoilerCombi(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_BOILER_COMBI))));
  }

  private Object doIfValidBoilerCombi(BoilerCombinationHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    commonBoilerCogenValidation(form.getApplicableLegislation(), form.getEfficiencyRating(), bindingResult);
    if (bindingResult.hasErrors()) {
      return getBoilerCombinationHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  @GetMapping("/cogeneration-space-heaters")
  public ModelAndView renderCogenerationSpaceHeaters(@ModelAttribute("form") CogenerationSpaceHeatersForm form) {
    return getCogenerationSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/cogeneration-space-heaters")
  @ResponseBody
  public Object handleCogenerationSpaceHeatersSubmit(@Valid @ModelAttribute("form") CogenerationSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidCogeneration(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/cogeneration-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCogenerationSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CogenerationSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidCogeneration(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_COGEN))));
  }

  private Object doIfValidCogeneration(CogenerationSpaceHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    commonBoilerCogenValidation(form.getApplicableLegislation(), form.getEfficiencyRating(), bindingResult);
    if (bindingResult.hasErrors()) {
      return getCogenerationSpaceHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  private void commonBoilerCogenValidation(String applicableLegislation, String efficiencyRating, BindingResult bindingResult) {
    if (!StringUtils.isBlank(applicableLegislation) && !StringUtils.isBlank(efficiencyRating)) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(applicableLegislation, SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(efficiencyRating, category)) {
        bindingResult.rejectValue("efficiencyRating", "efficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }
  }


  @GetMapping("/low-temperature-heat-pump-space-heaters")
  public ModelAndView renderLowTemperatureHeatPumpSpaceHeaters(@ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form) {
    return getLowTemperatureHeatPumpSpaceHeaters(Collections.emptyList());
  }

  private void validateLowTempEfficiencyRating(LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getLowTempEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(form.getLowTempEfficiencyRating(), category)) {
        bindingResult.rejectValue("lowTempEfficiencyRating", "lowTempEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }
  }

  @PostMapping("/low-temperature-heat-pump-space-heaters")
  @ResponseBody
  public Object handleLowTemperatureHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidLowTemp(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/low-temperature-heat-pump-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLowTemperatureHeatPumpSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidLowTemp(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getLowTempEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_LOW_TEMP))));
  }

  private Object doIfValidLowTemp(LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    validateLowTempEfficiencyRating(form, bindingResult);

    if (bindingResult.hasErrors()) {
      return getLowTemperatureHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  @GetMapping("/heat-pump-space-heaters")
  public ModelAndView renderHeatPumpSpaceHeaters(@ModelAttribute("form") HeatPumpSpaceHeatersForm form) {
    return getHeatPumpSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-space-heaters")
  @ResponseBody
  public Object handleHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidHeatPump(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/heat-pump-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    return doIfValidHeatPump(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getLowTempEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_HEAT_PUMP))));
  }

  private Object doIfValidHeatPump(HeatPumpSpaceHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    validateLowTempEfficiencyRating(form, bindingResult);

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
      return function.apply(category);
    }
  }

  @GetMapping("/heat-pump-combination-heaters")
  public ModelAndView renderHeatPumpCombinationHeaters(@ModelAttribute("form") HeatPumpCombinationHeatersForm form) {
    return getHeatPumpCombinationHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-combination-heaters")
  @ResponseBody
  public Object handleHeatPumpCombinationHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpCombinationHeatersForm form, BindingResult bindingResult) {
    return doIfValidCombinationHeater(form, bindingResult, (category -> documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, category))));
  }

  @PostMapping(value = "/heat-pump-combination-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpCombinationHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatPumpCombinationHeatersForm form, BindingResult bindingResult) {
    return doIfValidCombinationHeater(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getSpaceHeatingEfficiencyRating(), category, ProductMetadata.SPACE_HEATER_HEAT_PUMP_COMBINATION))));
  }

  private Object doIfValidCombinationHeater(HeatPumpCombinationHeatersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getSpaceHeatingEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isPrimaryRatingClassValid(form.getSpaceHeatingEfficiencyRating(), category)) {
        bindingResult.rejectValue("spaceHeatingEfficiencyRating", "spaceHeatingEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }

    if (!StringUtils.isBlank(form.getApplicableLegislation()) && !StringUtils.isBlank(form.getWaterHeatingEfficiencyRating())) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      if (!LegislationCategory.isSecondaryRatingClassValid(form.getWaterHeatingEfficiencyRating(), category)) {
        bindingResult.rejectValue("waterHeatingEfficiencyRating", "waterHeatingEfficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }

    if (bindingResult.hasErrors()) {
      return getHeatPumpCombinationHeaters(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), SpaceHeatersService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  @GetMapping("/package-space-heater")
  public ModelAndView renderSpaceHeaterPackages(@ModelAttribute("form") SpaceHeaterPackagesForm form) {
    return getSpaceHeaterPackages(Collections.emptyList());
  }

  @PostMapping("/package-space-heater")
  @ResponseBody
  public Object handleSpaceHeaterPackagesSubmit(@Valid @ModelAttribute("form") SpaceHeaterPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSpaceHeaterPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/package-space-heater", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelSpaceHeaterPackagesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") SpaceHeaterPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSpaceHeaterPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getPackageEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_PACKAGES, ProductMetadata.SPACE_HEATER_PACKAGE));
    }
  }

  @GetMapping("/package-combination-heater")
  public ModelAndView renderCombinationHeaterPackages(@ModelAttribute("form") CombinationHeaterPackagesForm form) {
    return getCombinationHeaterPackages(Collections.emptyList());
  }

  @PostMapping("/package-combination-heater")
  @ResponseBody
  public Object handleCombinationHeaterPackagesSubmit(@Valid @ModelAttribute("form") CombinationHeaterPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCombinationHeaterPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/package-combination-heater", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCombinationHeaterPackagesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CombinationHeaterPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCombinationHeaterPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getPackageSpaceHeatingEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_PACKAGES, ProductMetadata.SPACE_HEATER_PACKAGE_COMBINATION));
    }
  }

  private ModelAndView getBoilerSpaceHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/boilerSpaceHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderBoilerSpaceHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Boiler space heaters");
    return modelAndView;
  }

  private ModelAndView getBoilerCombinationHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/boilerCombinationHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderBoilerCombinationHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Boiler combination heaters");
    return modelAndView;
  }

  private ModelAndView getCogenerationSpaceHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/cogenerationSpaceHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderCogenerationSpaceHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Cogeneration space heaters");
    return modelAndView;
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

  private ModelAndView getHeatPumpCombinationHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/heatPumpCombinationHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderHeatPumpCombinationHeaters(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heat pump combination heaters");
    return modelAndView;
  }

  private ModelAndView getSpaceHeaterPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/spaceHeaterPackages");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackages(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Package space heaters");
    return modelAndView;
  }

  private ModelAndView getCombinationHeaterPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/combinationHeaterPackages");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderCombinationHeaterPackages(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Package combination heaters");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(SpaceHeatersService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(SpaceHeatersService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("secondaryEfficiencyRating", ControllerUtils.combinedLegislationCategorySecondaryRangesToSelectionMap(SpaceHeatersService.LEGISLATION_CATEGORIES));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
      Arrays.stream(LoadProfile.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        SpaceHeatersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
