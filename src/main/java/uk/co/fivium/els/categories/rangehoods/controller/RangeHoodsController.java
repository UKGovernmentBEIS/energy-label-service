package uk.co.fivium.els.categories.rangehoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.rangehoods.model.RangeHoodsForm;
import uk.co.fivium.els.categories.rangehoods.service.RangeHoodsService;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories")
public class RangeHoodsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Range Hoods";

  private final PdfRenderer pdfRenderer;
  private final RangeHoodsService rangeHoodsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public RangeHoodsController(PdfRenderer pdfRenderer, RangeHoodsService rangeHoodsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.rangeHoodsService = rangeHoodsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/range-hoods")
  public ModelAndView renderRangeHoodsForm(@ModelAttribute("form") RangeHoodsForm form) {
    return getRangeHoodsForm(Collections.emptyList());
  }

  @PostMapping("/range-hoods")
  @ResponseBody
  public Object handleRangeHoodsFormSubmit(@Valid @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {

    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), RangeHoodsService.LEGISLATION_CATEGORIES, bindingResult);

    if (bindingResult.hasErrors()) {
      return getRangeHoodsForm(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), RangeHoodsService.LEGISLATION_CATEGORIES);
      Resource pdf = pdfRenderer.render(rangeHoodsService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "range-hoods-label.pdf");
    }
  }


  private ModelAndView getRangeHoodsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/range-hoods/rangeHoods");
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(RangeHoodsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(RangeHoodsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("secondaryRating", ControllerUtils.ratingRangeToSelectionMap(RangeHoodsService.SECONDARY_CLASS_RANGE));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(RangeHoodsController.class).handleRangeHoodsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
