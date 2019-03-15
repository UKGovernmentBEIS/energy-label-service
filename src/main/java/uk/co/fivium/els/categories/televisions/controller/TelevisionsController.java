package uk.co.fivium.els.categories.televisions.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.televisions.model.TelevisionSubCategory;
import uk.co.fivium.els.categories.televisions.model.TelevisionsForm;
import uk.co.fivium.els.categories.televisions.service.TelevisionsService;
import uk.co.fivium.els.model.LegislationCategory;
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
@RequestMapping("/categories/televisions")
public class TelevisionsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Televisions";

  private final PdfRenderer pdfRenderer;
  private final TelevisionsService televisionsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public TelevisionsController(PdfRenderer pdfRenderer, TelevisionsService televisionsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.televisionsService = televisionsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderTelevisionsSubCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getTelevisionsSubCategory(Collections.emptyList());
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleTelevisionSubCategoriesSubmit(@ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required", TelevisionSubCategory.getNoSelectionErrorMessage());
      return getTelevisionsSubCategory(bindingResult.getFieldErrors());
    } else {
      TelevisionSubCategory subCategory = TelevisionSubCategory.valueOf(form.getCategory());
      return new ModelAndView("redirect:" + subCategory.getNextStateUrl());
    }
  }

  private ModelAndView getTelevisionsSubCategory(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(TelevisionSubCategory.getCategoryQuestionText(),
        TelevisionSubCategory.values(),
        errors,
        ReverseRouter.route(on(TelevisionsController.class).handleTelevisionSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
        BREADCRUMB_STAGE_TEXT,
        breadcrumbService
    );
  }

  @GetMapping("/televisions-from-2017")
  public ModelAndView renderTelevisionsFrom2017(@ModelAttribute("form") TelevisionsForm form) {
    return getTelevisionsFrom2017(Collections.emptyList());
  }

  @PostMapping("/televisions-from-2017")
  @ResponseBody
  public Object handleTelevisionsFrom2017Submit(@Valid @ModelAttribute("form") TelevisionsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getTelevisionsFrom2017(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(televisionsService.generateHtml(form, TelevisionSubCategory.TELEVISIONS_FROM_JAN2017));
      return ControllerUtils.serveResource(pdf, "televisions-label.pdf");
    }
  }

  @GetMapping("/televisions-from-2020")
  public ModelAndView renderTelevisionsFrom2020(@ModelAttribute("form") TelevisionsForm form) {
    return getTelevisionsFrom2020(Collections.emptyList());
  }

  @PostMapping("/televisions-from-2020")
  @ResponseBody
  public Object handleTelevisionsFrom2020Submit(@Valid @ModelAttribute("form") TelevisionsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getTelevisionsFrom2020(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(televisionsService.generateHtml(form, TelevisionSubCategory.TELEVISIONS_FROM_JAN2020));
      return ControllerUtils.serveResource(pdf, "televisions-label.pdf");
    }
  }

  private ModelAndView getTelevisionsFrom2017(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/televisions/televisions");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TelevisionsController.class).renderTelevisionsFrom2017(null)), TelevisionsService.LEGISLATION_CATEGORY_JAN2017);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Televisions from 1 January 2017");
    return modelAndView;
  }

  private ModelAndView getTelevisionsFrom2020(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/televisions/televisions");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TelevisionsController.class).renderTelevisionsFrom2020(null)), TelevisionsService.LEGISLATION_CATEGORY_JAN2020);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Televisions from 1 January 2020");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList, String submitUrl, LegislationCategory legislationCategory) {
    RatingClassRange efficiencyRatingRange = legislationCategory.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(TelevisionsController.class).renderTelevisionsSubCategories(null)));
  }

}
