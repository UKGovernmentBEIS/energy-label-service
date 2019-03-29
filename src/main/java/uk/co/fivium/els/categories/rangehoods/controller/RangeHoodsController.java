package uk.co.fivium.els.categories.rangehoods.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.DocumentRendererService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class RangeHoodsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Range Hoods";

  private final RangeHoodsService rangeHoodsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RangeHoodsController(RangeHoodsService rangeHoodsService,
                              BreadcrumbService breadcrumbService,
                              InternetLabelService internetLabelService,
                              DocumentRendererService documentRendererService) {
    this.rangeHoodsService = rangeHoodsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/range-hoods")
  public ModelAndView renderRangeHoodsForm(@ModelAttribute("form") RangeHoodsForm form) {
    return getRangeHoodsForm(Collections.emptyList());
  }

  @PostMapping("/range-hoods")
  @ResponseBody
  public Object handleRangeHoodsFormSubmit(@Valid @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> documentRendererService.processPdfResponse(rangeHoodsService.generateHtml(form, category))));
  }

  @PostMapping(value = "/range-hoods", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelRangeHoodsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.RANGE_HOODS))));
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
