package uk.co.fivium.els.categories.ventilationunits.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
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
import uk.co.fivium.els.categories.ventilationunits.model.VentilationUnitSubCategory;
import uk.co.fivium.els.categories.ventilationunits.model.VentilationUnitSubCategoryForm;
import uk.co.fivium.els.categories.ventilationunits.model.VentilationUnitsForm;
import uk.co.fivium.els.categories.ventilationunits.service.VentilationUnitsService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/ventilation-units")
public class VentilationUnitsController {

  private final PdfRenderer pdfRenderer;
  private final VentilationUnitsService ventilationUnitsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public VentilationUnitsController(PdfRenderer pdfRenderer, VentilationUnitsService ventilationUnitsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.ventilationUnitsService = ventilationUnitsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderVentilationUnitsSubCategories(@ModelAttribute("form") VentilationUnitSubCategoryForm form) {
    return getSubCategory();
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleVentilationUnitSubCategoriesSubmit(@Valid @ModelAttribute("form") VentilationUnitSubCategoryForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSubCategory();
    }
    else {
      VentilationUnitSubCategory subCategory = VentilationUnitSubCategory.valueOf(form.getSubCategory());
      return new ModelAndView("redirect:" + subCategory.getNextSateUrl());
    }
  }

  private ModelAndView getSubCategory() {
    ModelAndView modelAndView = new ModelAndView("categories/ventilation-units/ventilationUnitsSubCategory");
    modelAndView.addObject("subCategories",
      Arrays.stream(VentilationUnitSubCategory.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, VentilationUnitSubCategory::getDisplayName))
    );
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(VentilationUnitsController.class).handleVentilationUnitSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Ventilation units", ReverseRouter.route(on(VentilationUnitsController.class).renderVentilationUnitsSubCategories(null)));
    return modelAndView;
  }

  @GetMapping("/unidirectional-ventilation-units")
  public ModelAndView renderUnidirectionalVentilationUnits(@ModelAttribute("form") VentilationUnitsForm form) {
    return getUnidirectionalVentilationUnits(Collections.emptyList());
  }

  @PostMapping("/unidirectional-ventilation-units")
  @ResponseBody
  public Object handleUnidirectionalVentilationUnitsSubmit(@Valid @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getUnidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(ventilationUnitsService.generateHtml(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, VentilationUnitSubCategory.UNIDIRECTIONAL_VENTILATION_UNITS));
      return ControllerUtils.serveResource(pdf, "ventilation-units-label.pdf");
    }
  }

  @GetMapping("/bidirectional-ventilation-units")
  public ModelAndView renderBidirectionalVentilationUnits(@ModelAttribute("form") VentilationUnitsForm form) {
    return getBidirectionalVentilationUnits(Collections.emptyList());
  }

  @PostMapping("/bidirectional-ventilation-units")
  @ResponseBody
  public Object handleBidirectionalVentilationUnitsSubmit(@Valid @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getBidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(ventilationUnitsService.generateHtml(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, VentilationUnitSubCategory.BIDIRECTIONAL_VENTILATION_UNITS));
      return ControllerUtils.serveResource(pdf, "ventilation-units-label.pdf");
    }
  }

  private ModelAndView getUnidirectionalVentilationUnits(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/ventilation-units/unidirectionalVentilationUnits");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Unidirectional ventilation units");
    return modelAndView;
  }

  private ModelAndView getBidirectionalVentilationUnits(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/ventilation-units/bidirectionalVentilationUnits");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(VentilationUnitsController.class).renderBidirectionalVentilationUnits(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Bidirectional ventilation units");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Ventilation units", ReverseRouter.route(on(VentilationUnitsController.class).renderVentilationUnitsSubCategories(null)));
  }

}
