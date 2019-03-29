package uk.co.fivium.els.categories.televisions.controller;

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
import uk.co.fivium.els.categories.televisions.model.TelevisionsForm;
import uk.co.fivium.els.categories.televisions.service.TelevisionsService;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.DocumentRendererService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class TelevisionController {

  private static final String BREADCRUMB_STAGE_TEXT = "Televisions";

  private final TelevisionsService televisionsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public TelevisionController(TelevisionsService televisionsService,
                              BreadcrumbService breadcrumbService,
                              InternetLabelService internetLabelService,
                              DocumentRendererService documentRendererService) {
    this.televisionsService = televisionsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/televisions")
  public ModelAndView renderTelevisionsForm(@ModelAttribute("form") TelevisionsForm form) {
    return getTelevisionsForm(Collections.emptyList());
  }

  @PostMapping("/televisions")
  @ResponseBody
  public Object handleTelevisionsFormSubmit(@Valid @ModelAttribute("form") TelevisionsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, ((category) -> documentRendererService.processPdfResponse(televisionsService.generateHtml(form, category))));
  }

  @PostMapping(value = "/televisions", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelTelevisionsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") TelevisionsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, ((category) ->
        documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.TV))));
  }

  private Object doIfValid(TelevisionsForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> supplier) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), TelevisionsService.LEGISLATION_CATEGORIES, bindingResult);
    if (bindingResult.hasErrors()) {
      return getTelevisionsForm(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), TelevisionsService.LEGISLATION_CATEGORIES);
      return supplier.apply(category);
    }
  }

  private ModelAndView getTelevisionsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/televisions/televisions");
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(TelevisionsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(TelevisionsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(TelevisionController.class).handleTelevisionsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
