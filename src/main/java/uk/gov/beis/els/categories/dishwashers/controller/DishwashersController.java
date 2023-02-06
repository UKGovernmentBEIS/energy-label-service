package uk.gov.beis.els.categories.dishwashers.controller;

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
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.model.RescaledInternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class DishwashersController {

  private final DishwashersService dishwashersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public DishwashersController(DishwashersService dishwashersService,
                               BreadcrumbService breadcrumbService,
                               InternetLabelService internetLabelService,
                               DocumentRendererService documentRendererService) {
    this.dishwashersService = dishwashersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/dishwashers")
  public ModelAndView renderDishwashers(@ModelAttribute("form") DishwashersForm form) {
    return getModelAndView();
  }

  @PostMapping("/dishwashers")
  @ResponseBody
  public Object handleDishwashersSubmit(@Valid @ModelAttribute("form") DishwashersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processResponse(dishwashersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/dishwashers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelDishwashersSubmit(@Validated({InternetLabellingGroup.class, RescaledInternetLabellingGroup.class}) @ModelAttribute("form") DishwashersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), DishwashersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.DISHWASHERS));
    }
  }

  private ModelAndView getModelAndView() {
    return getModelAndView(Collections.emptyList());
  }

  private ModelAndView getModelAndView(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/dishwashers/dishwashers");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(DishwashersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange()));
    modelAndView.addObject("noiseEmissionsRating", ControllerUtils.ratingRangeToSelectionMap(DishwashersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange()));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(DishwashersController.class).renderDishwashers(null)));
    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Dishwashers");

    return modelAndView;
  }



}
