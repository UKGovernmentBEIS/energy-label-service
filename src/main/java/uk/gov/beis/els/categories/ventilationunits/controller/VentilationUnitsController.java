package uk.gov.beis.els.categories.ventilationunits.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

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
import uk.gov.beis.els.categories.ventilationunits.model.VentilationUnitCategory;
import uk.gov.beis.els.categories.ventilationunits.model.VentilationUnitsForm;
import uk.gov.beis.els.categories.ventilationunits.service.VentilationUnitsService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/ventilation-units")
public class VentilationUnitsController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Ventilation units";

  private final VentilationUnitsService ventilationUnitsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public VentilationUnitsController(VentilationUnitsService ventilationUnitsService,
                                    BreadcrumbService breadcrumbService,
                                    InternetLabelService internetLabelService,
                                    DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, VentilationUnitCategory.GET, VentilationUnitsController.class);
    this.ventilationUnitsService = ventilationUnitsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/unidirectional-ventilation-units")
  public ModelAndView renderUnidirectionalVentilationUnits(@ModelAttribute("form") VentilationUnitsForm form) {
    return getUnidirectionalVentilationUnits(Collections.emptyList());
  }

  @PostMapping("/unidirectional-ventilation-units")
  @ResponseBody
  public Object handleUnidirectionalVentilationUnitsSubmit(@Valid @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getUnidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(ventilationUnitsService.generateHtmlUnidirectional(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/unidirectional-ventilation-units", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelUnidirectionalVentilationUnitsSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getUnidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.VENTILATION_UNITS_UNIDIRECTIONAL));
    }
  }

  @GetMapping("/bidirectional-ventilation-units")
  public ModelAndView renderBidirectionalVentilationUnits(@ModelAttribute("form") VentilationUnitsForm form) {
    return getBidirectionalVentilationUnits(Collections.emptyList());
  }

  @PostMapping("/bidirectional-ventilation-units")
  @ResponseBody
  public Object handleBidirectionalVentilationUnitsSubmit(@Valid @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(ventilationUnitsService.generateHtmlBidirectional(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/bidirectional-ventilation-units", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBidirectionalVentilationUnitsSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") VentilationUnitsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBidirectionalVentilationUnits(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.VENTILATION_UNITS_BIDIRECTIONAL));
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
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(VentilationUnitsController.class).renderCategories(null)));
  }

}
