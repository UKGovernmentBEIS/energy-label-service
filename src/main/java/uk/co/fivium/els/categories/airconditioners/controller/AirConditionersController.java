package uk.co.fivium.els.categories.airconditioners.controller;

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
import uk.co.fivium.els.categories.airconditioners.model.AirConditionersCategory;
import uk.co.fivium.els.categories.airconditioners.model.CoolingDuctedAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.CoolingDuctlessAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.HeatingDuctedAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.HeatingDuctlessAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.ReversibleDuctedAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.ReversibleDuctlessAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.service.AirConditionersService;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.DocumentRendererService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/air-conditioners")
public class AirConditionersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Air conditioners";

  private final AirConditionersService airConditionersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public AirConditionersController(AirConditionersService airConditionersService,
                                   BreadcrumbService breadcrumbService,
                                   InternetLabelService internetLabelService,
                                   DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, AirConditionersCategory.GET, AirConditionersController.class);
    this.airConditionersService = airConditionersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
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
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/non-duct/cooling-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CoolingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_COOLING_ONLY_NON_DUCT));
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
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/non-duct/heating-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class)@ModelAttribute("form") HeatingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getAverageHeatingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_HEATING_ONLY_NON_DUCT));
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
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/non-duct/reversible-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelReversibleDuctlessAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") ReversibleDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_REVERSIBLE_NON_DUCT));
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
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/single-or-double-duct/cooling-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCoolingDuctedAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CoolingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_COOLING_ONLY_DUCT));
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
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/single-or-double-duct/heating-only-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelHeatingDuctedAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") HeatingDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getHeatingDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getHeatingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_HEATING_ONLY_DUCT));
    }
  }

  @GetMapping("/single-or-double-duct/reversible-air-conditioners")
  public ModelAndView renderReversibleDuctedAirConditioners(@ModelAttribute("form") ReversibleDuctedAirConditionersForm form) {
    return getReversibleDuctedAirConditioners(Collections.emptyList());
  }

  @PostMapping("/single-or-double-duct/reversible-air-conditioners")
  @ResponseBody
  public Object handleReversibleDuctedAirConditionersSubmit(@Valid @ModelAttribute("form") ReversibleDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
    }
  }

  @PostMapping(value = "/single-or-double-duct/reversible-air-conditioners", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelReversibleDuctedAirConditionersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") ReversibleDuctedAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctedAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getCoolingEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_JAN2019, ProductMetadata.AC_REVERSIBLE_DUCT));
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

  private ModelAndView getReversibleDuctedAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/reversibleDuctedAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderReversibleDuctedAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Reversible ducted air conditioners");
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
