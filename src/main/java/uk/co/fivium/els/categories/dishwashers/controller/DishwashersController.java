package uk.co.fivium.els.categories.dishwashers.controller;

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
import uk.co.fivium.els.categories.dishwashers.model.DishwashersForm;
import uk.co.fivium.els.categories.dishwashers.service.DishwashersService;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class DishwashersController {

  private final PdfRenderer pdfRenderer;
  private final DishwashersService dishwashersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;

  @Autowired
  public DishwashersController(PdfRenderer pdfRenderer,
                               DishwashersService dishwashersService,
                               BreadcrumbService breadcrumbService,
                               InternetLabelService internetLabelService) {
    this.pdfRenderer = pdfRenderer;
    this.dishwashersService = dishwashersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
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
    } else {
      Resource pdf = pdfRenderer.render(dishwashersService.generateHtml(form, DishwashersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "dishwashers-label.pdf");
    }
  }

  @PostMapping(value = "/dishwashers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelDishwashersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") DishwashersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), DishwashersService.LEGISLATION_CATEGORY_CURRENT, "dishwashers");
    }
  }

  private ModelAndView getModelAndView() {
    return getModelAndView(Collections.emptyList());
  }

  private ModelAndView getModelAndView(List<FieldError> errorList) {
    RatingClassRange efficiencyRatingRange = DishwashersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    RatingClassRange dryingEfficiencyRange = DishwashersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();

    ModelAndView modelAndView = new ModelAndView("categories/dishwashers/dishwashers");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("dryingEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(dryingEfficiencyRange));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(DishwashersController.class).renderDishwashers(null)));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Dishwashers");

    return modelAndView;
  }



}
