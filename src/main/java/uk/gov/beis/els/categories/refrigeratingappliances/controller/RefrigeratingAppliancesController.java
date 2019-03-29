package uk.gov.beis.els.categories.refrigeratingappliances.controller;

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
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FreezerStarRating;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.gov.beis.els.categories.refrigeratingappliances.model.RefrigeratingAppliancesCategory;
import uk.gov.beis.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
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
    if (bindingResult.hasErrors()) {
      return getFridgesFreezers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(householdRefrigeratingAppliancesService.generateHtml(form));
    }
  }

  @PostMapping(value = "/household-fridges-and-freezers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelFridgesFreezersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") FridgesFreezersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getFridgesFreezers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.HRA_FRIDGE_FREEZER));
    }
  }

  @GetMapping("/wine-storage-appliances")
  public ModelAndView renderWineStorageAppliances(@ModelAttribute("form") WineStorageAppliancesForm form) {
    return getWineStorageAppliances(Collections.emptyList());
  }

  @PostMapping("/wine-storage-appliances")
  @ResponseBody
  public Object handleWineStorageAppliancesSubmit(@Valid @ModelAttribute("form") WineStorageAppliancesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWineStorageAppliances(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(householdRefrigeratingAppliancesService.generateHtml(form));
    }
  }

  @PostMapping(value = "/wine-storage-appliances", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWineStorageAppliancesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") WineStorageAppliancesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWineStorageAppliances(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.HRA_WINE_STORAGE));
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
    RatingClassRange efficiencyRatingRange = RefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        RefrigeratingAppliancesController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
