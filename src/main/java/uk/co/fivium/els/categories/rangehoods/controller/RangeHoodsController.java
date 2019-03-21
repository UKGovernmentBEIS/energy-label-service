package uk.co.fivium.els.categories.rangehoods.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
import uk.co.fivium.els.categories.rangehoods.model.RangeHoodsForm;
import uk.co.fivium.els.categories.rangehoods.service.RangeHoodsService;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class RangeHoodsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Range Hoods";

  private final PdfRenderer pdfRenderer;
  private final RangeHoodsService rangeHoodsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;

  @Autowired
  public RangeHoodsController(PdfRenderer pdfRenderer, RangeHoodsService rangeHoodsService, BreadcrumbService breadcrumbService, InternetLabelService internetLabelService) {
    this.pdfRenderer = pdfRenderer;
    this.rangeHoodsService = rangeHoodsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
  }

  @GetMapping("/range-hoods")
  public ModelAndView renderRangeHoodsForm(@ModelAttribute("form") RangeHoodsForm form) {
    return getRangeHoodsForm(Collections.emptyList());
  }

  @PostMapping("/range-hoods")
  @ResponseBody
  public Object handleRangeHoodsFormSubmit(@Valid @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> {
      Resource pdf = pdfRenderer.render(rangeHoodsService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "range-hoods-label.pdf");
    }));
  }

  @PostMapping(value = "/range-hoods", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelRangeHoodsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, "range-hoods")));
  }

  private Object doIfValid(RangeHoodsForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function){
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), RangeHoodsService.LEGISLATION_CATEGORIES, bindingResult);

    if (bindingResult.hasErrors()) {
      return getRangeHoodsForm(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), RangeHoodsService.LEGISLATION_CATEGORIES);
      return function.apply(category);
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
