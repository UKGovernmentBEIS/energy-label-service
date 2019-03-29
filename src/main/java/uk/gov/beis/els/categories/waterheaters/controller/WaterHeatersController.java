package uk.gov.beis.els.categories.waterheaters.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import uk.gov.beis.els.categories.waterheaters.model.ConventionalWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.SolarWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterHeaterCategory;
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
  public Object handleHeatPumpWaterHeatersSubmit(@Valid @ModelAttribute("form") HeatPumpWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/heat-pump-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatPumpWaterHeatersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") HeatPumpWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatPumpWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_HEAT_PUMP));
    }
  }

  @GetMapping("/conventional-water-heaters")
  public ModelAndView renderConventionalWaterHeaters(@ModelAttribute("form") ConventionalWaterHeatersForm form) {
    return getConventionalWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/conventional-water-heaters")
  @ResponseBody
  public Object handleConventionalWaterHeatersSubmit(@Valid @ModelAttribute("form") ConventionalWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getConventionalWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/conventional-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelConventionalWaterHeatersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") ConventionalWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getConventionalWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_CONVENTIONAL));
    }
  }

  @GetMapping("/solar-water-heaters")
  public ModelAndView renderSolarWaterHeaters(@ModelAttribute("form") SolarWaterHeatersForm form) {
    return getSolarWaterHeaters(Collections.emptyList());
  }

  @PostMapping("/solar-water-heaters")
  @ResponseBody
  public Object handleSolarWaterHeatersSubmit(@Valid @ModelAttribute("form") SolarWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolarWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/solar-water-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelSolarWaterHeatersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") SolarWaterHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSolarWaterHeaters(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_SOLAR));
    }
  }

  @GetMapping("/hot-water-storage-tanks")
  public ModelAndView renderHotWaterStorageTanks(@ModelAttribute("form") HotWaterStorageTanksForm form) {
    return getHotWaterStorageTanks(Collections.emptyList());
  }

  @PostMapping("/hot-water-storage-tanks")
  @ResponseBody
  public Object handleHotWaterStorageTanksSubmit(@Valid @ModelAttribute("form") HotWaterStorageTanksForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHotWaterStorageTanks(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/hot-water-storage-tanks", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHotWaterStorageTanksSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") HotWaterStorageTanksForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHotWaterStorageTanks(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_STORAGE_TANKS));
    }
  }

  @GetMapping("/packages-of-water-heater-and-solar-device")
  public ModelAndView renderWaterSolarPackages(@ModelAttribute("form") WaterSolarPackagesForm form) {
    return getWaterSolarPackages(Collections.emptyList());
  }

  @PostMapping("/packages-of-water-heater-and-solar-device")
  @ResponseBody
  public Object handleWaterSolarPackagesSubmit(@Valid @ModelAttribute("form") WaterSolarPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES));
    }
  }

  @PostMapping(value = "/packages-of-water-heater-and-solar-device", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWaterSolarPackagesSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") WaterSolarPackagesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWaterSolarPackages(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getPackageEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES, ProductMetadata.WATER_HEATERS_PACKAGE));
    }
  }

  private ModelAndView getHeatPumpWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/heatPumpWaterHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderHeatPumpWaterHeaters(null)), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heat pump water heaters");
    return modelAndView;
  }

  private ModelAndView getConventionalWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/conventionalWaterHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderConventionalWaterHeaters(null)), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Conventional water heaters");

    modelAndView.addObject("energyUnitKw", Collections.singletonMap(EnergyConsumptionUnit.KWH.name(), EnergyConsumptionUnit.KWH.getDisplayName()));
    modelAndView.addObject("energyUnitGj", Collections.singletonMap(EnergyConsumptionUnit.GJ.name(), EnergyConsumptionUnit.GJ.getDisplayName()));
    modelAndView.addObject("energyUnitBoth", Collections.singletonMap(EnergyConsumptionUnit.BOTH.name(), EnergyConsumptionUnit.BOTH.getDisplayName()));

    return modelAndView;
  }

  private ModelAndView getSolarWaterHeaters(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/solarWaterHeaters");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderSolarWaterHeaters(null)), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Solar water heaters");
    return modelAndView;
  }

  private ModelAndView getHotWaterStorageTanks(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/hotWaterStorageTanks");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderHotWaterStorageTanks(null)), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Hot water storage tanks");
    return modelAndView;
  }

  private ModelAndView getWaterSolarPackages(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/water-heaters/waterSolarPackages");

    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(WaterHeatersController.class).renderWaterSolarPackages(null)), WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES);
    modelAndView.addObject("secondaryEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES.getSecondaryRatingRange()));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Packages of water heater and solar device");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl, LegislationCategory legislationCategory) {
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(legislationCategory.getPrimaryRatingRange()));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("loadProfile",
      Arrays.stream(LoadProfile.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LoadProfile::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(WaterHeatersController.class).renderCategories(null)));
  }
}
