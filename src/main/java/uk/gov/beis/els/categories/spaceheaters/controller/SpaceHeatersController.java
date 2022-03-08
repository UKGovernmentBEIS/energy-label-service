package uk.gov.beis.els.categories.spaceheaters.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.common.StandardCategoryForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CombinationHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.PreferentialHeaterTypes;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterCategory;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesCategory;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesPreferentialHeaterCategory;
import uk.gov.beis.els.categories.spaceheaters.model.TankLabelClass;
import uk.gov.beis.els.categories.spaceheaters.model.TemperatureControlClass;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
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
  private final Category spaceHeaterPackagesCategory = SpaceHeaterPackagesCategory.GET;
  private final Category spaceHeaterPackagesPreferentialHeaterCategory = SpaceHeaterPackagesPreferentialHeaterCategory.GET;

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
    if (bindingResult.hasErrors()) {
      return getBoilerSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/boiler-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBoilerSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") BoilerSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBoilerSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_BOILER));
    }
  }

  @GetMapping("/boiler-combination-heaters")
  public ModelAndView renderBoilerCombinationHeaters(@ModelAttribute("form") BoilerCombinationHeatersForm form) {
    return getBoilerCombinationHeaters(Collections.emptyList());
  }

  @PostMapping("/boiler-combination-heaters")
  @ResponseBody
  public Object handleBoilerCombinationHeatersSubmit(@Valid @ModelAttribute("form") BoilerCombinationHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBoilerCombinationHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/boiler-combination-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBoilerCombinationHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") BoilerCombinationHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBoilerCombinationHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_BOILER_COMBI));
    }
  }

  @GetMapping("/cogeneration-space-heaters")
  public ModelAndView renderCogenerationSpaceHeaters(@ModelAttribute("form") CogenerationSpaceHeatersForm form) {
    return getCogenerationSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/cogeneration-space-heaters")
  @ResponseBody
  public Object handleCogenerationSpaceHeatersSubmit(@Valid @ModelAttribute("form") CogenerationSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCogenerationSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/cogeneration-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCogenerationSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CogenerationSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCogenerationSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_COGEN));
    }
  }

  @GetMapping("/low-temperature-heat-pump-space-heaters")
  public ModelAndView renderLowTemperatureHeatPumpSpaceHeaters(@ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form) {
    return getLowTemperatureHeatPumpSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/low-temperature-heat-pump-space-heaters")
  @ResponseBody
  public Object handleLowTemperatureHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLowTemperatureHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/low-temperature-heat-pump-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLowTemperatureHeatPumpSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LowTemperatureHeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLowTemperatureHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getLowTempEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_LOW_TEMP));
    }
  }

  @GetMapping("/heat-pump-space-heaters")
  public ModelAndView renderHeatPumpSpaceHeaters(@ModelAttribute("form") HeatPumpSpaceHeatersForm form) {
    return getHeatPumpSpaceHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-space-heaters")
  @ResponseBody
  public Object handleHeatPumpSpaceHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/heat-pump-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatPumpSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpSpaceHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getLowTempEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_HEAT_PUMP));
    }
  }

  @GetMapping("/heat-pump-combination-heaters")
  public ModelAndView renderHeatPumpCombinationHeaters(@ModelAttribute("form") HeatPumpCombinationHeatersForm form) {
    return getHeatPumpCombinationHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-combination-heaters")
  @ResponseBody
  public Object handleHeatPumpCombinationHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpCombinationHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpCombinationHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/heat-pump-combination-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpCombinationHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatPumpCombinationHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpCombinationHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getSpaceHeatingEfficiencyRating(), SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_HEAT_PUMP_COMBINATION));
    }
  }

  @GetMapping("/packages-sort-question")
  public ModelAndView renderSpaceHeaterPackagesSortQuestion(@ModelAttribute("form") StandardCategoryForm form) {
    return getSpaceHeaterPackagesSortingQuestion(Collections.emptyList());
  }

  @PostMapping("/packages-sort-question")
  @ResponseBody
  public ModelAndView handleSpaceHeaterPackagesSortQuestionSubmit(
      @Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required",
          spaceHeaterPackagesCategory.getNoSelectionErrorMessage());
      return getSpaceHeaterPackagesSortingQuestion(bindingResult.getFieldErrors());
    } else {
      CategoryItem categoryItem = spaceHeaterPackagesCategory.getCategoryItem(form.getCategory());
      return new ModelAndView("redirect:" + categoryItem.getNextStateUrl());
    }
  }

  @GetMapping("/packages-preferential-heater-type")
  public ModelAndView renderSpaceHeaterPackagesPreferentialHeaterSortQuestion(@ModelAttribute("form") StandardCategoryForm form) {
    return getSpaceHeaterPackagesPreferentialHeaterQuestion(Collections.emptyList());
  }

  @PostMapping("/packages-preferential-heater-type")
  @ResponseBody
  public ModelAndView handleSpaceHeaterPackagesPreferentialHeaterSortQuestionSubmit(
      @Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required",
          spaceHeaterPackagesPreferentialHeaterCategory.getNoSelectionErrorMessage());
      return getSpaceHeaterPackagesSortingQuestion(bindingResult.getFieldErrors());
    } else {
      CategoryItem categoryItem = spaceHeaterPackagesPreferentialHeaterCategory.getCategoryItem(form.getCategory());
      return new ModelAndView("redirect:" + categoryItem.getNextStateUrl());
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

  @GetMapping("/package-space-heater/boiler/calculator")
  public ModelAndView renderSpaceHeaterPackagesBoilerCalculator(@ModelAttribute("form") BoilerPackagesCalculatorForm form) {
    return getSpaceHeatersPackagesCalculator(Collections.emptyList(), PreferentialHeaterTypes.BOILER);
  }

  @PostMapping("/package-space-heater/boiler/calculator")
  @ResponseBody
  public Object handleSpaceHeaterPackagesBoilerCalculatorSubmit(@Valid @ModelAttribute("form") BoilerPackagesCalculatorForm form,
                                                                BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSpaceHeatersPackagesCalculator(bindingResult.getFieldErrors(), PreferentialHeaterTypes.BOILER);
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(
          spaceHeatersService.toSpaceHeaterPackagesForm(form))
      );
    }
  }

  @GetMapping("/package-space-heater/heat-pump/calculator")
  public ModelAndView renderSpaceHeaterPackagesHeatPumpCalculator(@ModelAttribute("form") HeatPumpPackagesCalculatorForm form) {
    return getSpaceHeatersPackagesCalculator(Collections.emptyList(), PreferentialHeaterTypes.HEAT_PUMP);
  }

  @PostMapping("/package-space-heater/heat-pump/calculator")
  @ResponseBody
  public Object handleSpaceHeaterPackagesHeatPumpCalculatorSubmit(@Valid @ModelAttribute("form") HeatPumpPackagesCalculatorForm form,
                                                                  BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSpaceHeatersPackagesCalculator(bindingResult.getFieldErrors(), PreferentialHeaterTypes.HEAT_PUMP);
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(
          spaceHeatersService.toSpaceHeaterPackagesForm(form)));
    }
  }

  @GetMapping("/package-space-heater/cogeneration-heater/calculator")
  public ModelAndView renderSpaceHeaterPackagesCogenerationCalculator(@ModelAttribute("form") CogenerationPackagesCalculatorForm form) {
    return getSpaceHeatersPackagesCalculator(Collections.emptyList(), PreferentialHeaterTypes.COGENERATION_HEATER);
  }

  @PostMapping("/package-space-heater/cogeneration-heater/calculator")
  @ResponseBody
  public Object handleSpaceHeaterPackagesCogenerationCalculatorSubmit(@Valid @ModelAttribute("form") CogenerationPackagesCalculatorForm form,
                                                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSpaceHeatersPackagesCalculator(bindingResult.getFieldErrors(),
          PreferentialHeaterTypes.COGENERATION_HEATER);
    } else {
      return documentRendererService.processPdfResponse(spaceHeatersService.generateHtml(
          spaceHeatersService.toSpaceHeaterPackagesForm(form)));
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
    breadcrumbService.pushBreadcrumb(modelAndView, "Package space heaters",
        ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesSortQuestion(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label");
    return modelAndView;
  }

  private ModelAndView getCombinationHeaterPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/combinationHeaterPackages");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(SpaceHeatersController.class).renderCombinationHeaterPackages(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Package combination heaters");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    RatingClassRange secondaryEfficiencyRatingRange = SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("secondaryEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(secondaryEfficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
      Arrays.stream(LoadProfile.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    RatingClassRange packageEfficiencyRatingRange = SpaceHeatersService.LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange();
    modelAndView.addObject("packageEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(packageEfficiencyRatingRange));
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        SpaceHeatersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

  private ModelAndView getSpaceHeaterPackagesSortingQuestion(List<FieldError> errors) {
    ModelAndView modelAndView = new ModelAndView("calculatorQuestion")
        .addObject("categories",
            spaceHeaterPackagesCategory.getCategoryItems()
                .stream()
                .collect(StreamUtils.toLinkedHashMap(CategoryItem::getId, CategoryItem::getName))
        )
        .addObject("submitUrl", ReverseRouter.route(
            on(SpaceHeatersController.class).handleSpaceHeaterPackagesSortQuestionSubmit(null,
                ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errors);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT,
        ReverseRouter.route(on(SpaceHeatersController.class).renderCategories(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Package space heaters");
    return modelAndView;
  }

  private ModelAndView getSpaceHeaterPackagesPreferentialHeaterQuestion(List<FieldError> errors) {
    ModelAndView modelAndView = new ModelAndView("categories/space-heaters/preferentialHeaterQuestion")
        .addObject("categories",
            spaceHeaterPackagesPreferentialHeaterCategory.getCategoryItems()
                .stream()
                .collect(StreamUtils.toLinkedHashMap(CategoryItem::getId, CategoryItem::getName))
        )
        .addObject("submitUrl", ReverseRouter.route(
            on(SpaceHeatersController.class).handleSpaceHeaterPackagesPreferentialHeaterSortQuestionSubmit(null,
                ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errors);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT,
        ReverseRouter.route(on(SpaceHeatersController.class).renderCategories(null)));
    breadcrumbService.pushBreadcrumb(modelAndView, "Package space heaters",
        ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesSortQuestion(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Preferential heater");
    return modelAndView;
  }

  private ModelAndView getSpaceHeatersPackagesCalculator(List<FieldError> errors, PreferentialHeaterTypes preferentialHeaterType) {
    ModelAndView modelAndView;

    switch (preferentialHeaterType) {
      case BOILER:
        modelAndView = new ModelAndView("categories/space-heaters/spaceHeaterPackagesBoilerCalculator");
        addCommonObjects(modelAndView, errors,
            ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesBoilerCalculator(null)));
        break;
      case HEAT_PUMP:
        modelAndView = new ModelAndView("categories/space-heaters/spaceHeaterPackagesHeatPumpCalculator");
        addCommonObjects(modelAndView, errors,
            ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesHeatPumpCalculator(null)));
        break;
      case COGENERATION_HEATER:
        modelAndView = new ModelAndView("categories/space-heaters/spaceHeaterPackagesCogenerationCalculator");
        addCommonObjects(modelAndView, errors,
            ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesCogenerationCalculator(null)));
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + preferentialHeaterType);
    }

    modelAndView.addObject("temperatureControlClass",
        Arrays.stream(TemperatureControlClass.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, Enum::name)));
    modelAndView.addObject("storageTankRating",
        Arrays.stream(TankLabelClass.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, TankLabelClass::getDisplayName)));
    breadcrumbService.pushBreadcrumb(modelAndView, "Package space heaters",
        ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesSortQuestion(null)));
    breadcrumbService.pushBreadcrumb(modelAndView, "Preferential heater",
        ReverseRouter.route(on(SpaceHeatersController.class).renderSpaceHeaterPackagesPreferentialHeaterSortQuestion(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Calculator");
    return modelAndView;
  }

}
