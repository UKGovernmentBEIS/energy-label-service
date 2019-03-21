package uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.model.ClimateClass;
import uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.model.ProfessionalRefrigeratedStorageCabinetsForm;
import uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.service.ProfessionalRefrigeratedStorageCabinetsService;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories")
public class ProfessionalRefrigeratedStorageCabinetsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Professional refrigerated storage cabinets";

  private final PdfRenderer pdfRenderer;
  private final ProfessionalRefrigeratedStorageCabinetsService professionalRefrigeratedStorageCabinetsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public ProfessionalRefrigeratedStorageCabinetsController(PdfRenderer pdfRenderer, ProfessionalRefrigeratedStorageCabinetsService professionalRefrigeratedStorageCabinetsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.professionalRefrigeratedStorageCabinetsService = professionalRefrigeratedStorageCabinetsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/professional-refrigerated-storage-cabinets")
  public ModelAndView renderProfessionalRefrigeratedStorageCabinetsForm(@ModelAttribute("form") ProfessionalRefrigeratedStorageCabinetsForm form) {
    return getProfessionalRefrigeratedStorageCabinetsForm(Collections.emptyList());
  }

  @PostMapping("/professional-refrigerated-storage-cabinets")
  @ResponseBody
  public Object handleProfessionalRefrigeratedStorageCabinetsFormSubmit(@Valid @ModelAttribute("form") ProfessionalRefrigeratedStorageCabinetsForm form, BindingResult bindingResult) {

    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), ProfessionalRefrigeratedStorageCabinetsService.LEGISLATION_CATEGORIES, bindingResult);

    if (bindingResult.hasErrors()) {
      return getProfessionalRefrigeratedStorageCabinetsForm(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), ProfessionalRefrigeratedStorageCabinetsService.LEGISLATION_CATEGORIES);
      Resource pdf = pdfRenderer.render(professionalRefrigeratedStorageCabinetsService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "professional-refrigerated-storage-cabinets-label.pdf");
    }
  }


  private ModelAndView getProfessionalRefrigeratedStorageCabinetsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/professional-refrigerated-storage-cabinets/professionalRefrigeratedStorageCabinets");
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(ProfessionalRefrigeratedStorageCabinetsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(ProfessionalRefrigeratedStorageCabinetsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(ProfessionalRefrigeratedStorageCabinetsController.class).handleProfessionalRefrigeratedStorageCabinetsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    modelAndView.addObject("climateClass",
      Arrays.stream(ClimateClass.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, ClimateClass::getDisplayName))
    );
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
