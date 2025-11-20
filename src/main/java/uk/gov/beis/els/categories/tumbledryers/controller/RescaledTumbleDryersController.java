package uk.gov.beis.els.categories.tumbledryers.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.model.RescaledInternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.tumbledryers.model.rescaled.RescaledTumbleDryersForm;
import uk.gov.beis.els.categories.tumbledryers.service.RescaledTumbleDryersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/tumble-dryers/rescaled")
public class RescaledTumbleDryersController {
  private static final String BREADCRUMB_STAGE_TEXT = "Rescaled tumble dryers";

  private final BreadcrumbService breadcrumbService;
  private final DocumentRendererService documentRendererService;
  private final RescaledTumbleDryersService rescaledTumbleDryersService;
  private final InternetLabelService internetLabelService;

  public RescaledTumbleDryersController(BreadcrumbService breadcrumbService,
                                        DocumentRendererService documentRendererService,
                                        RescaledTumbleDryersService rescaledTumbleDryersService,
                                        InternetLabelService internetLabelService) {
    this.breadcrumbService = breadcrumbService;
    this.documentRendererService = documentRendererService;
    this.rescaledTumbleDryersService = rescaledTumbleDryersService;
    this.internetLabelService = internetLabelService;
  }

  @GetMapping
  public ModelAndView renderRescaledTumbleDryerForm(@ModelAttribute("form") RescaledTumbleDryersForm form) {
    return getRescaledTumbleDryerModelAndView(List.of());
  }

  @PostMapping
  public Object handleRescaledTumbleDryerFormSubmit(@Valid @ModelAttribute("form") RescaledTumbleDryersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getRescaledTumbleDryerModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processResponse(rescaledTumbleDryersService.generateHtml(form));
    }
  }

  @PostMapping(params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelRescaledTumbleDryerFormSubmit(@Validated({InternetLabellingGroup.class, RescaledInternetLabellingGroup.class}) @ModelAttribute("form") RescaledTumbleDryersForm form, BindingResult bindingResult) {
    
    // Rescaled tumble dryers are special as they do not allow the internal label orientation to be specified, they must always point left.
    // So we hide the option on the form, remove the mand validation error, and hardcode the orientation directly.
    
    var cleanBindingResult = removeLabelOrientationError(bindingResult, form);
    form.setLabelOrientation(InternetLabelOrientation.LEFT.name());
    
    if (cleanBindingResult.hasErrors()) {
      return getRescaledTumbleDryerModelAndView(cleanBindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RescaledTumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_RESCALED));
    }
  }
  
  private BindingResult removeLabelOrientationError(BindingResult bindingResult, RescaledTumbleDryersForm form) {
    var filteredErrors = bindingResult.getFieldErrors().stream()
      .filter(fieldError -> !fieldError.getField().equals("labelOrientation"))
      .toList();
    
    var filteredBindingResult = new BeanPropertyBindingResult(form, "form");
    filteredErrors.forEach(filteredBindingResult::addError);
    
    return filteredBindingResult;
  }
  
  private ModelAndView getRescaledTumbleDryerModelAndView(List<FieldError> errors) {
    var modelAndView = new ModelAndView("categories/tumble-dryers/rescaledTumbleDryers");

    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(DishwashersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange()));
    modelAndView.addObject("noiseEmissionsClass", ControllerUtils.ratingRangeToSelectionMap(DishwashersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange()));
    modelAndView.addObject("condensationEfficiencyClass", ControllerUtils.ratingRangeToSelectionMap(DishwashersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange()));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(RescaledTumbleDryersController.class).renderRescaledTumbleDryerForm(null)));

    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    ControllerUtils.addErrorSummary(modelAndView, errors);

    breadcrumbService.addBreadcrumbToModel(modelAndView);
    breadcrumbService.pushBreadcrumb(modelAndView, TumbleDryerTypeController.BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(TumbleDryerTypeController.class).renderTumbleDryerTypeForm(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, BREADCRUMB_STAGE_TEXT);

    return modelAndView;
  }
}
