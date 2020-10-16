package uk.gov.beis.els.categories.refrigeratingappliances.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
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
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FreezerStarRating;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.gov.beis.els.categories.refrigeratingappliances.model.RefrigeratingAppliancesCategory;
import uk.gov.beis.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;
import uk.gov.beis.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/household-refrigerating-appliances")
public class RefrigeratingAppliancesController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Household refrigerating appliances";

  private final RefrigeratingAppliancesService householdRefrigeratingAppliancesService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RefrigeratingAppliancesController(RefrigeratingAppliancesService householdRefrigeratingAppliancesService,
                                           BreadcrumbService breadcrumbService,
                                           InternetLabelService internetLabelService,
                                           DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, RefrigeratingAppliancesCategory.GET, RefrigeratingAppliancesController.class);
    this.householdRefrigeratingAppliancesService = householdRefrigeratingAppliancesService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/household-fridges-and-freezers")
  public ModelAndView renderFridgesFreezers(@ModelAttribute("form") FridgesFreezersForm form) {
    return getFridgesFreezers(Collections.emptyList());
  }

  @PostMapping("/household-fridges-and-freezers")
  @ResponseBody
  public Object handleFridgesFreezersSubmit(@Valid @ModelAttribute("form") FridgesFreezersForm form, BindingResult bindingResult) {
    return doIfValidFridgesFreezersForm(form, bindingResult, (category -> documentRendererService.processPdfResponse(householdRefrigeratingAppliancesService.generateHtml(form, category))));
  }

  @PostMapping(value = "/household-fridges-and-freezers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelFridgesFreezersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") FridgesFreezersForm form, BindingResult bindingResult) {
    ControllerUtils.validateInternetLabelColour(form.getApplicableLegislation(), RefrigeratingAppliancesService.LEGISLATION_CATEGORY_POST_MARCH_2021, bindingResult);
    return doIfValidFridgesFreezersForm(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.HRA_FRIDGE_FREEZER))));
  }

  @GetMapping("/wine-storage-appliances")
  public ModelAndView renderWineStorageAppliances(@ModelAttribute("form") WineStorageAppliancesForm form) {
    return getWineStorageAppliances(Collections.emptyList());
  }

  @PostMapping("/wine-storage-appliances")
  @ResponseBody
  public Object handleWineStorageAppliancesSubmit(@Valid @ModelAttribute("form") WineStorageAppliancesForm form, BindingResult bindingResult) {
    return doIfValidWineStorageAppliancesForm(form, bindingResult, (category -> documentRendererService.processPdfResponse(householdRefrigeratingAppliancesService.generateHtml(form, category))));
  }

  @PostMapping(value = "/wine-storage-appliances", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWineStorageAppliancesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") WineStorageAppliancesForm form, BindingResult bindingResult) {
    ControllerUtils.validateInternetLabelColour(form.getApplicableLegislation(), RefrigeratingAppliancesService.LEGISLATION_CATEGORY_POST_MARCH_2021, bindingResult);
    return doIfValidWineStorageAppliancesForm(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.HRA_WINE_STORAGE))));
  }

  private Object doIfValidWineStorageAppliancesForm(WineStorageAppliancesForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), RefrigeratingAppliancesService.LEGISLATION_CATEGORIES, bindingResult);
    if (bindingResult.hasErrors()) {
      return getWineStorageAppliances(bindingResult.getFieldErrors());
    } else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), RefrigeratingAppliancesService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  private Object doIfValidFridgesFreezersForm(FridgesFreezersForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), RefrigeratingAppliancesService.LEGISLATION_CATEGORIES, bindingResult);
    if (bindingResult.hasErrors()) {
      return getFridgesFreezers(bindingResult.getFieldErrors());
    } else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), RefrigeratingAppliancesService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  private ModelAndView getFridgesFreezers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/household-refrigerating-appliances/fridgesFreezers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratingAppliancesController.class).renderFridgesFreezers(null)));
    modelAndView.addObject("starRating",
      Arrays.stream(FreezerStarRating.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, FreezerStarRating::getDisplayValue))
    );
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Household fridges and freezers");
    return modelAndView;
  }

  private ModelAndView getWineStorageAppliances(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/household-refrigerating-appliances/wineStorageAppliances");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratingAppliancesController.class).renderWineStorageAppliances(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Wine storage appliances");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    modelAndView.addObject("legislationCategories", ControllerUtils.legislationCategorySelection(RefrigeratingAppliancesService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(RefrigeratingAppliancesService.LEGISLATION_CATEGORIES));
    // Noise rating only for post march 2021
    modelAndView.addObject("noiseEmissionsRating", ControllerUtils.ratingRangeToSelectionMap(RefrigeratingAppliancesService.LEGISLATION_CATEGORY_POST_MARCH_2021.getSecondaryRatingRange()));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        RefrigeratingAppliancesController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
