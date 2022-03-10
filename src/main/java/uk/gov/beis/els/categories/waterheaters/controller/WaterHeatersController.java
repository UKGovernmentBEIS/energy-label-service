package uk.gov.beis.els.categories.waterheaters.controller;

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
import uk.gov.beis.els.categories.waterheaters.model.ConventionalWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.SolarWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterHeaterCategory;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCategory;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;
import uk.gov.beis.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/water-heaters")
public class WaterHeatersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Water heaters and storage tanks";

  private final WaterHeatersService waterHeatersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;
  private final Category waterHeaterPackageCategory = WaterSolarPackagesCategory.GET;

  @Autowired
  public WaterHeatersController(WaterHeatersService waterHeatersService,
                                BreadcrumbService breadcrumbService,
                                InternetLabelService internetLabelService,
                                DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, WaterHeaterCategory.GET, WaterHeatersController.class);
    this.waterHeatersService = waterHeatersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/heat-pump-water-heaters")
  public ModelAndView renderHeatPumpWaterHeaters(@ModelAttribute("form") HeatPumpWaterHeatersForm form) {
    return getHeatPumpWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/heat-pump-water-heaters")
  @ResponseBody
  public Object handleHeatPumpWaterHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpWaterHeatersForm form,
                                                 BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/heat-pump-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpWaterHeatersSubmit(
      @Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatPumpWaterHeatersForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(
          internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
              WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_HEAT_PUMP));
    }
  }

  @GetMapping("/conventional-water-heaters")
  public ModelAndView renderConventionalWaterHeaters(@ModelAttribute("form") ConventionalWaterHeatersForm form) {
    return getConventionalWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/conventional-water-heaters")
  @ResponseBody
  public Object handleConventionalWaterHeatersSubmit(@Valid @ModelAttribute("form") ConventionalWaterHeatersForm form,
                                                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getConventionalWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/conventional-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelConventionalWaterHeatersSubmit(
      @Validated(InternetLabellingGroup.class) @ModelAttribute("form") ConventionalWaterHeatersForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getConventionalWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(
          internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
              WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_CONVENTIONAL));
    }
  }

  @GetMapping("/solar-water-heaters")
  public ModelAndView renderSolarWaterHeaters(@ModelAttribute("form") SolarWaterHeatersForm form) {
    return getSolarWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/solar-water-heaters")
  @ResponseBody
  public Object handleSolarWaterHeatersSubmit(@Valid @ModelAttribute("form") SolarWaterHeatersForm form,
                                              BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolarWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/solar-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelSolarWaterHeatersSubmit(
      @Validated(InternetLabellingGroup.class) @ModelAttribute("form") SolarWaterHeatersForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolarWaterHeaters(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(
          internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
              WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_SOLAR));
    }
  }

  @GetMapping("/hot-water-storage-tanks")
  public ModelAndView renderHotWaterStorageTanks(@ModelAttribute("form") HotWaterStorageTanksForm form) {
    return getHotWaterStorageTanks(Collections.emptyList());
  }

  @PostMapping("/hot-water-storage-tanks")
  @ResponseBody
  public Object handleHotWaterStorageTanksSubmit(@Valid @ModelAttribute("form") HotWaterStorageTanksForm form,
                                                 BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHotWaterStorageTanks(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/hot-water-storage-tanks", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHotWaterStorageTanksSubmit(
      @Validated(InternetLabellingGroup.class) @ModelAttribute("form") HotWaterStorageTanksForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHotWaterStorageTanks(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(
          internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
              WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_STORAGE_TANKS));
    }
  }

  @GetMapping("/packages-sort-question")
  public ModelAndView renderWaterSolarPackagesSortQuestion(@ModelAttribute("form") StandardCategoryForm form) {
    return getWaterSolarPackagesSortingQuestion(Collections.emptyList());
  }

  @PostMapping("/packages-sort-question")
  @ResponseBody
  public ModelAndView handleWaterSolarPackagesSortQuestionSubmit(
      @Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required",
          waterHeaterPackageCategory.getNoSelectionErrorMessage());
      return getWaterSolarPackagesSortingQuestion(bindingResult.getFieldErrors());
    } else {
      CategoryItem categoryItem = waterHeaterPackageCategory.getCategoryItem(form.getCategory());
      return new ModelAndView("redirect:" + categoryItem.getNextStateUrl());
    }
  }

  @GetMapping("/packages-of-water-heater-and-solar-device/calculator")
  public ModelAndView renderWaterSolarPackagesCalculator(@ModelAttribute("form") WaterSolarPackagesCalculatorForm form) {
    return getWaterSolarPackagesCalculator(Collections.emptyList());
  }

  @PostMapping(value = "/packages-of-water-heater-and-solar-device/calculator", params = "Download label")
  @ResponseBody
  public Object handleWaterSolarPackagesCalculatorSubmit(@Valid @ModelAttribute("form") WaterSolarPackagesCalculatorForm form,
                                                         BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackagesCalculator(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(waterHeatersService.toWaterSolarPackagesForm(form),
              WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES)
      );
    }
  }

  @PostMapping(value = "/packages-of-water-heater-and-solar-device/calculator", params = "Download fiche")
  @ResponseBody
  public Object handleWaterSolarPackagesFicheSubmit(@Valid @ModelAttribute("form") WaterSolarPackagesCalculatorForm form,
                                                         BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackagesCalculator(bindingResult.getFieldErrors());
    } else {
      //TODO download fiche instead
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(waterHeatersService.toWaterSolarPackagesForm(form),
              WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES)
      );
    }
  }

  @GetMapping("/packages-of-water-heater-and-solar-device")
  public ModelAndView renderWaterSolarPackages(@ModelAttribute("form") WaterSolarPackagesForm form) {
    return getWaterSolarPackages(Collections.emptyList());
  }

  @PostMapping(value = "/packages-of-water-heater-and-solar-device")
  @ResponseBody
  public Object handleWaterSolarPackagesSubmit(@Valid @ModelAttribute("form") WaterSolarPackagesForm form,
                                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackages(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(
          waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES));
    }
  }

  @PostMapping(value = "/packages-of-water-heater-and-solar-device", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWaterSolarPackagesSubmit(
      @Validated(InternetLabellingGroup.class) @ModelAttribute("form") WaterSolarPackagesForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackages(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(
          internetLabelService.generateInternetLabel(form, form.getPackageEfficiencyRating(),
              WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES, ProductMetadata.WATER_HEATERS_PACKAGE));
    }
  }

  private ModelAndView getHeatPumpWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/heatPumpWaterHeaters");
    addCommonObjects(modelAndView, errorList,
        ReverseRouter.route(on(WaterHeatersController.class).renderHeatPumpWaterHeaters(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heat pump water heaters");
    addEnergyUnits(modelAndView);
    return modelAndView;
  }

  private ModelAndView getConventionalWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/conventionalWaterHeaters");
    addCommonObjects(modelAndView, errorList,
        ReverseRouter.route(on(WaterHeatersController.class).renderConventionalWaterHeaters(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Conventional water heaters");
    addEnergyUnits(modelAndView);
    return modelAndView;
  }

  private ModelAndView getSolarWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/solarWaterHeaters");
    addCommonObjects(modelAndView, errorList,
        ReverseRouter.route(on(WaterHeatersController.class).renderSolarWaterHeaters(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Solar water heaters");
    addEnergyUnits(modelAndView);
    return modelAndView;
  }

  private ModelAndView getHotWaterStorageTanks(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/hotWaterStorageTanks");
    addCommonObjects(modelAndView, errorList,
        ReverseRouter.route(on(WaterHeatersController.class).renderHotWaterStorageTanks(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Hot water storage tanks");
    return modelAndView;
  }

  private ModelAndView getWaterSolarPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/waterSolarPackages");

    addCommonObjects(modelAndView, errorList,
        ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackages(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES);
    modelAndView.addObject("secondaryEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(
        WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES.getSecondaryRatingRange()));
    modelAndView.addObject("loadProfile", WaterHeatersService.WATER_SOLAR_PACKAGES_LOAD_PROFILES.stream()
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName)));
    breadcrumbService.pushBreadcrumb(modelAndView, "Packages of water heater and solar device",
        ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackagesSortQuestion(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList, String submitUrl,
                                LegislationCategory legislationCategory) {
    modelAndView.addObject("efficiencyRating",
        ControllerUtils.ratingRangeToSelectionMap(legislationCategory.getPrimaryRatingRange()));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
        Arrays.stream(LoadProfile.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT,
        ReverseRouter.route(on(WaterHeatersController.class).renderCategories(null)));
  }

  private void addEnergyUnits(ModelAndView modelAndView) {
    modelAndView.addObject("energyUnitKw",
        Collections.singletonMap(EnergyConsumptionUnit.KWH.name(), EnergyConsumptionUnit.KWH.getDisplayName()));
    modelAndView.addObject("energyUnitGj",
        Collections.singletonMap(EnergyConsumptionUnit.GJ.name(), EnergyConsumptionUnit.GJ.getDisplayName()));
    modelAndView.addObject("energyUnitBoth",
        Collections.singletonMap(EnergyConsumptionUnit.BOTH.name(), EnergyConsumptionUnit.BOTH.getDisplayName()));
  }

  private ModelAndView getWaterSolarPackagesSortingQuestion(List<FieldError> errors) {
    ModelAndView modelAndView = new ModelAndView("calculatorQuestion")
        .addObject("categories",
            waterHeaterPackageCategory.getCategoryItems()
                .stream()
                .collect(StreamUtils.toLinkedHashMap(CategoryItem::getId, CategoryItem::getName))
        )
        .addObject("submitUrl", ReverseRouter.route(
            on(WaterHeatersController.class).handleWaterSolarPackagesSortQuestionSubmit(null,
                ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errors);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT,
        ReverseRouter.route(on(WaterHeatersController.class).renderCategories(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Packages of water heater and solar device");
    return modelAndView;
  }

  private ModelAndView getWaterSolarPackagesCalculator(List<FieldError> errors) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/waterSolarPackagesCalculator");
    addCommonObjects(modelAndView, errors,
        ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackagesCalculator(null)),
        WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES);
    modelAndView.addObject("loadProfile", WaterHeatersService.WATER_SOLAR_PACKAGES_LOAD_PROFILES.stream()
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName)));
    breadcrumbService.pushBreadcrumb(modelAndView, "Packages of water heater and solar device",
        ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackagesSortQuestion(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Calculator");
    return modelAndView;
  }
}
