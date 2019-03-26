package uk.co.fivium.els.categories.airconditioners.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import uk.co.fivium.els.categories.airconditioners.model.*;
import uk.co.fivium.els.categories.airconditioners.service.AirConditionersService;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/air-conditioners")
public class AirConditionersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Air conditioners";

  private final PdfRenderer pdfRenderer;
  private final AirConditionersService airConditionersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;

  @Autowired
  public AirConditionersController(PdfRenderer pdfRenderer,
                                   AirConditionersService airConditionersService,
                                   BreadcrumbService breadcrumbService,
                                   InternetLabelService internetLabelService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, AirConditionersCategory.GET, AirConditionersController.class);
    this.pdfRenderer = pdfRenderer;
    this.airConditionersService = airConditionersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
  }

  @GetMapping("/non-duct/cooling-only-air-conditioners")
  public ModelAndView renderCoolingDuctlessAirConditioners(@ModelAttribute("form") CoolingDuctlessAirConditionersForm form) {
    return getCoolingDuctlessAirConditioners(Collections.emptyList());
  }

  @PostMapping("/non-duct/cooling-only-air-conditioners")
  @ResponseBody
  public Object handleCoolingDuctlessAirConditionersSubmit(@Valid @ModelAttribute("form") CoolingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }
  }

  @PostMapping(value = "/non-duct/cooling-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CoolingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, "cooling-only-air-conditioners");
    }
  }

  @GetMapping("/non-duct/heating-only-air-conditioners")
  public ModelAndView renderHeatingDuctlessAirConditioners(@ModelAttribute("form") HeatingDuctlessAirConditionersForm form) {
    return getHeatingDuctlessAirConditioners(Collections.emptyList());
  }

  @PostMapping("/non-duct/heating-only-air-conditioners")
  @ResponseBody
  public Object handleCoolingDuctlessAirConditionersSubmit(@Valid @ModelAttribute("form") HeatingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }
  }

  @PostMapping(value = "/non-duct/heating-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") HeatingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return internetLabelService.generateInternetLabel(form, form.getAverageHeatingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, "heating-only-air-conditioners");
    }
  }

  @GetMapping("/non-duct/reversible-air-conditioners")
  public ModelAndView renderReversibleDuctlessAirConditioners(@ModelAttribute("form") ReversibleDuctlessAirConditionersForm form) {
    return getReversibleDuctlessAirConditioners(Collections.emptyList());
  }

  @PostMapping("/non-duct/reversible-air-conditioners")
  @ResponseBody
  public Object handleReversibleDuctlessAirConditionersSubmit(@Valid @ModelAttribute("form") ReversibleDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }
  }

  @PostMapping(value = "/non-duct/reversible-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelReversibleDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") ReversibleDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, "reversible-air-conditioners");
    }
  }

  @GetMapping("/single-or-double-duct/cooling-only-air-conditioners")
  public ModelAndView renderCoolingDuctedAirConditioners(@ModelAttribute("form") CoolingDuctedAirConditionersForm form) {
    return getCoolingDuctedAirConditioners(Collections.emptyList());
  }

  @PostMapping("/single-or-double-duct/cooling-only-air-conditioners")
  @ResponseBody
  public Object handleCoolingDuctedAirConditionersSubmit(@Valid @ModelAttribute("form") CoolingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }
  }

  @PostMapping(value = "/single-or-double-duct/cooling-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctedAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CoolingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, "cooling-only-air-conditioners");
    }
  }

  @GetMapping("/single-or-double-duct/heating-only-air-conditioners")
  public ModelAndView renderHeatingDuctedAirConditioners(@ModelAttribute("form") HeatingDuctedAirConditionersForm form) {
    return getHeatingDuctedAirConditioners(Collections.emptyList());
  }

  @PostMapping("/single-or-double-duct/heating-only-air-conditioners")
  @ResponseBody
  public Object handleHeatingDuctedAirConditionersSubmit(@Valid @ModelAttribute("form") HeatingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }
  }

  @PostMapping(value = "/single-or-double-duct/heating-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatingDuctedAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return internetLabelService.generateInternetLabel(form, form.getHeatingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, "heating-only-air-conditioners");
    }
  }

  private ModelAndView getCoolingDuctlessAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/coolingDuctlessAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderCoolingDuctlessAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Cooling-only ductless air conditioners");
    return modelAndView;
  }

  private ModelAndView getHeatingDuctlessAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/heatingDuctlessAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderHeatingDuctlessAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heating-only ductless air conditioners");
    return modelAndView;
  }

  private ModelAndView getReversibleDuctlessAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/reversibleDuctlessAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderReversibleDuctlessAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Reversible ductless air conditioners");
    return modelAndView;
  }

  private ModelAndView getCoolingDuctedAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/coolingDuctedAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderCoolingDuctedAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Cooling-only ducted air conditioners");
    return modelAndView;
  }

  private ModelAndView getHeatingDuctedAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/heatingDuctedAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderHeatingDuctedAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Heating-only ducted air conditioners");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList, String submitUrl) {
    RatingClassRange efficiencyRatingRange = AirConditionersService.LEGISLATION_CATEGORY_JAN2019.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
      AirConditionersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }
}
