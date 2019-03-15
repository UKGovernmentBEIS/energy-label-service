package uk.co.fivium.els.categories.refrigeratingappliances.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.refrigeratingappliances.model.RefrigeratingAppliancesCategory;
import uk.co.fivium.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.co.fivium.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.co.fivium.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/household-refrigerating-appliances")
public class RefrigeratingAppliancesController {

  private static final String BREADCRUMB_STAGE_TEXT = "Household refrigerating appliances";

  private final PdfRenderer pdfRenderer;
  private final RefrigeratingAppliancesService householdRefrigeratingAppliancesService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public RefrigeratingAppliancesController(PdfRenderer pdfRenderer, RefrigeratingAppliancesService householdRefrigeratingAppliancesService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.householdRefrigeratingAppliancesService = householdRefrigeratingAppliancesService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderRefrigeratingAppliancesSubCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getRefrigeratingAppliancesSubCategory(Collections.emptyList());
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleRefrigeratingApplianceSubCategoriesSubmit(@ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    return ControllerUtils.handleSubCategorySubmit(RefrigeratingAppliancesCategory.GET, form, bindingResult, this::getRefrigeratingAppliancesSubCategory);
  }

  private ModelAndView getRefrigeratingAppliancesSubCategory(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(RefrigeratingAppliancesCategory.GET,
        errors,
        ReverseRouter.route(on(RefrigeratingAppliancesController.class).handleRefrigeratingApplianceSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
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
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratingAppliancesController.class).renderFridgesFreezers(null)));
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
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        RefrigeratingAppliancesController.class).renderRefrigeratingAppliancesSubCategories(null)));
  }

}
