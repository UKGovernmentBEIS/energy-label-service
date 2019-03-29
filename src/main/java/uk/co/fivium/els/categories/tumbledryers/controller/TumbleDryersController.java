package uk.co.fivium.els.categories.tumbledryers.controller;

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
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryerCategory;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.service.TumbleDryersService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.DocumentRendererService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/tumble-dryers")
public class TumbleDryersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Tumble dryers";

  private final TumbleDryersService tumbleDryersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public TumbleDryersController(TumbleDryersService tumbleDryersService,
                                BreadcrumbService breadcrumbService,
                                InternetLabelService internetLabelService,
                                DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, TumbleDryerCategory.GET, TumbleDryersController.class);
    this.tumbleDryersService = tumbleDryersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/air-vented-tumble-dryers")
  public ModelAndView renderAirVentedTumbleDryers(@ModelAttribute("form") TumbleDryersForm form) {
    return getAirVentedTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/air-vented-tumble-dryers")
  @ResponseBody
  public Object handleAirVentedTumbleDryersSubmit(@Valid @ModelAttribute("form") TumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getAirVentedTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(tumbleDryersService.generateHtmlAirVented(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/air-vented-tumble-dryers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelAirVentedTumbleDryersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") TumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getAirVentedTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_AIR_VENTED));
    }
  }

  @GetMapping("/condenser-tumble-dryers")
  public ModelAndView renderCondenserTumbleDryers(@ModelAttribute("form") CondenserTumbleDryersForm form) {
    return getCondenserTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/condenser-tumble-dryers")
  @ResponseBody
  public Object handleCondenserTumbleDryersSubmit(@Valid @ModelAttribute("form") CondenserTumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCondenserTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(tumbleDryersService.generateHtmlCondenser(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/condenser-tumble-dryers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelCondenserTumbleDryersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") CondenserTumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCondenserTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_CONDENSER));
    }
  }

  @GetMapping("/gas-fired-tumble-dryers")
  public ModelAndView renderGasFiredTumbleDryers(@ModelAttribute("form") TumbleDryersForm form) {
    return getGasFiredTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/gas-fired-tumble-dryers")
  @ResponseBody
  public Object handleGasFiredTumbleDryersSubmit(@Valid @ModelAttribute("form") TumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getGasFiredTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(tumbleDryersService.generateHtmlGasFired(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/gas-fired-tumble-dryers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelGasFiredDryersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") TumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getGasFiredTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_GAS_FIRED));
    }
  }

  private ModelAndView getAirVentedTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/airVentedTumbleDryers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderAirVentedTumbleDryers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Air vented tumble dryers");
    return modelAndView;
  }

  private ModelAndView getCondenserTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/condenserTumbleDryers");
    RatingClassRange condensationEfficiencyRating = TumbleDryersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderCondenserTumbleDryers(null)));
    modelAndView.addObject("condensationEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(condensationEfficiencyRating));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Condenser tumble dryers");
    return modelAndView;
  }

  private ModelAndView getGasFiredTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/gasFiredTumbleDryers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderGasFiredTumbleDryers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Gas-fired tumble dryers");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = TumbleDryersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
