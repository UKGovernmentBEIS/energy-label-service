package uk.co.fivium.els.categories.televisions.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.televisions.model.TelevisionsForm;
import uk.co.fivium.els.categories.televisions.service.TelevisionsService;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class TelevisionController {

  private static final String BREADCRUMB_STAGE_TEXT = "Televisions";

  private final PdfRenderer pdfRenderer;
  private final TelevisionsService televisionsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public TelevisionController(PdfRenderer pdfRenderer, TelevisionsService televisionsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.televisionsService = televisionsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/televisions")
  public ModelAndView renderTelevisionsFrom(@ModelAttribute("form") TelevisionsForm form) {
    return getTelevisionsFrom(Collections.emptyList());
  }

  @PostMapping("/televisions")
  @ResponseBody
  public Object handleTelevisionsFromSubmit(@Valid @ModelAttribute("form") TelevisionsForm form, BindingResult bindingResult) {

    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), TelevisionsService.LEGISLATION_CATEGORIES, bindingResult);

    if (bindingResult.hasErrors()) {
      return getTelevisionsFrom(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), TelevisionsService.LEGISLATION_CATEGORIES);
      Resource pdf = pdfRenderer.render(televisionsService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "televisions-label.pdf");
    }
  }


  private ModelAndView getTelevisionsFrom(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/televisions/televisions");
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(TelevisionsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(TelevisionsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(TelevisionController.class).handleTelevisionsFromSubmit(null, ReverseRouter.emptyBindingResult())));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
