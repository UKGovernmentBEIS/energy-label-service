package uk.co.fivium.els.categories.dishwashers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.dishwashers.model.DishwashersForm;
import uk.co.fivium.els.categories.dishwashers.service.DishwashersService;
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
@RequestMapping("/categories")
public class DishwashersController {

  private final PdfRenderer pdfRenderer;
  private final DishwashersService dishwashersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public DishwashersController(PdfRenderer pdfRenderer,
                               DishwashersService dishwashersService,
                               BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.dishwashersService = dishwashersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/dishwashers")
  public ModelAndView renderDishwashers(@ModelAttribute("form") DishwashersForm form) {
    return getModelAndView();
  }

  @PostMapping("/dishwashers")
  @ResponseBody
  public Object handleDishwashersSubmit(@Valid @ModelAttribute("form") DishwashersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      Resource pdf = pdfRenderer.render(dishwashersService.generateHtml(form, DishwashersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "dishwashers-label.pdf");
    }
  }

  private ModelAndView getModelAndView() {
    return getModelAndView(Collections.emptyList());
  }

  private ModelAndView getModelAndView(List<FieldError> errorList) {
    RatingClassRange efficiencyRatingRange = DishwashersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    RatingClassRange dryingEfficiencyRange = DishwashersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();

    ModelAndView modelAndView = new ModelAndView("categories/dishwashers/dishwashers");
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("dryingEfficiencyRating", StreamUtils.ratingRangeToSelectionMap(dryingEfficiencyRange));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(DishwashersController.class).renderDishwashers(null)));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Dishwashers");

    return modelAndView;
  }



}
