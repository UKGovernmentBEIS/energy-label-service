package uk.co.fivium.els.categories.householdrefrigeratingappliances.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.model.FridgesFreezersForm;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.model.HouseholdRefrigeratingApplianceSubCategory;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.model.WineStorageAppliancesForm;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.service.HouseholdRefrigeratingAppliancesService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories/household-refrigerating-appliances")
public class HouseholdRefrigeratingAppliancesController {

  private static final String BREADCRUMB_STAGE_TEXT = "Household refrigerating appliances";

  private final PdfRenderer pdfRenderer;
  private final HouseholdRefrigeratingAppliancesService householdRefrigeratingAppliancesService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public HouseholdRefrigeratingAppliancesController(PdfRenderer pdfRenderer, HouseholdRefrigeratingAppliancesService householdRefrigeratingAppliancesService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.householdRefrigeratingAppliancesService = householdRefrigeratingAppliancesService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderHouseholdRefrigeratingAppliancesSubCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getHouseholdRefrigeratingAppliancesSubCategory(Collections.emptyList());
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleHouseholdRefrigeratingApplianceSubCategoriesSubmit(@ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required", HouseholdRefrigeratingApplianceSubCategory.getNoSelectionErrorMessage());
      return getHouseholdRefrigeratingAppliancesSubCategory(bindingResult.getFieldErrors());
    } else {
      HouseholdRefrigeratingApplianceSubCategory subCategory = HouseholdRefrigeratingApplianceSubCategory.valueOf(form.getCategory());
      return new ModelAndView("redirect:" + subCategory.getNextStateUrl());
    }
  }

  private ModelAndView getHouseholdRefrigeratingAppliancesSubCategory(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(HouseholdRefrigeratingApplianceSubCategory.getCategoryQuestionText(),
        HouseholdRefrigeratingApplianceSubCategory.values(),
        errors,
        ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).handleHouseholdRefrigeratingApplianceSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
        BREADCRUMB_STAGE_TEXT,
        breadcrumbService
    );
  }

  @GetMapping("/household-refrigerating-appliances")
  public ModelAndView renderFridgesFreezers(@ModelAttribute("form") FridgesFreezersForm form) {
    return getFridgesFreezers(Collections.emptyList());
  }

  @PostMapping("/household-refrigerating-appliances")
  @ResponseBody
  public Object handleFridgesFreezersSubmit(@Valid @ModelAttribute("form") FridgesFreezersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getFridgesFreezers(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(householdRefrigeratingAppliancesService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "household-refrigerating-appliances-label.pdf");
    }
  }

  @GetMapping("/wine-storage-appliances")
  public ModelAndView renderWineStorageAppliances(@ModelAttribute("form") WineStorageAppliancesForm form) {
    return getWineStorageAppliances(Collections.emptyList());
  }

  @PostMapping("/wine-storage-appliances")
  @ResponseBody
  public Object handleWineStorageAppliancesSubmit(@Valid @ModelAttribute("form") WineStorageAppliancesForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getWineStorageAppliances(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(householdRefrigeratingAppliancesService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "wine-storage-appliances-label.pdf");
    }
  }

  private ModelAndView getFridgesFreezers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/household-refrigerating-appliances/fridgesFreezers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).renderFridgesFreezers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Household fridges and freezers");
    return modelAndView;
  }

  private ModelAndView getWineStorageAppliances(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/household-refrigerating-appliances/wineStorageAppliances");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).renderWineStorageAppliances(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Wine storage appliances");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = HouseholdRefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).renderHouseholdRefrigeratingAppliancesSubCategories(null)));
  }

}
