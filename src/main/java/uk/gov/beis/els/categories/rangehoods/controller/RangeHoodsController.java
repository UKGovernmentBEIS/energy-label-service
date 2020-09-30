package uk.gov.beis.els.categories.rangehoods.controller;

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
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.rangehoods.model.RangeHoodsForm;
import uk.gov.beis.els.categories.rangehoods.service.RangeHoodsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

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
    if (bindingResult.hasErrors()) {
      return getRangeHoodsForm(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(rangeHoodsService.generateHtml(form, RangeHoodsService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/range-hoods", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelRangeHoodsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") RangeHoodsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getRangeHoodsForm(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RangeHoodsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.RANGE_HOODS));
    }
  }

  private ModelAndView getRangeHoodsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/range-hoods/rangeHoods");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(RangeHoodsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange()));
    modelAndView.addObject("secondaryRating", ControllerUtils.ratingRangeToSelectionMap(RangeHoodsService.SECONDARY_CLASS_RANGE));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(RangeHoodsController.class).handleRangeHoodsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
