package uk.co.fivium.els.categories.prorefrigeratedcabinets.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
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
import uk.co.fivium.els.categories.prorefrigeratedcabinets.model.ClimateClass;
import uk.co.fivium.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.co.fivium.els.categories.prorefrigeratedcabinets.service.ProRefrigeratedCabinetsService;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories")
public class ProRefrigeratedCabinetsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Professional refrigerated storage cabinets";

  private final PdfRenderer pdfRenderer;
  private final ProRefrigeratedCabinetsService professionalRefrigeratedStorageCabinetsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;

  @Autowired
  public ProRefrigeratedCabinetsController(PdfRenderer pdfRenderer,
                                           ProRefrigeratedCabinetsService professionalRefrigeratedStorageCabinetsService,
                                           BreadcrumbService breadcrumbService,
                                           InternetLabelService internetLabelService) {
    this.pdfRenderer = pdfRenderer;
    this.professionalRefrigeratedStorageCabinetsService = professionalRefrigeratedStorageCabinetsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
  }

  @GetMapping("/professional-refrigerated-storage-cabinets")
  public ModelAndView renderProfessionalRefrigeratedStorageCabinetsForm(@ModelAttribute("form") ProRefrigeratedCabinetsForm form) {
    return getProfessionalRefrigeratedStorageCabinetsForm(Collections.emptyList());
  }

  @PostMapping("/professional-refrigerated-storage-cabinets")
  @ResponseBody
  public Object handleProfessionalRefrigeratedStorageCabinetsFormSubmit(@Valid @ModelAttribute("form") ProRefrigeratedCabinetsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> {
      Resource pdf = pdfRenderer.render(professionalRefrigeratedStorageCabinetsService.generateHtml(form, category));
      return ControllerUtils.serveResource(pdf, "professional-refrigerated-storage-cabinets-label.pdf");
    }));
  }

  @PostMapping(value = "/professional-refrigerated-storage-cabinets", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelProfessionalRefrigeratedStorageCabinetsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") ProRefrigeratedCabinetsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, "professional-refrigerated-storage-cabinets")));
  }

  private Object doIfValid(ProRefrigeratedCabinetsForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), ProRefrigeratedCabinetsService.LEGISLATION_CATEGORIES, bindingResult);

    if (bindingResult.hasErrors()) {
      return getProfessionalRefrigeratedStorageCabinetsForm(bindingResult.getFieldErrors());
    }
    else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), ProRefrigeratedCabinetsService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  private ModelAndView getProfessionalRefrigeratedStorageCabinetsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/professional-refrigerated-storage-cabinets/professionalRefrigeratedStorageCabinets");
    modelAndView.addObject("legislationYears", ControllerUtils.legislationYearSelection(ProRefrigeratedCabinetsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(
        ProRefrigeratedCabinetsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(ProRefrigeratedCabinetsController.class).handleProfessionalRefrigeratedStorageCabinetsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    modelAndView.addObject("climateClass",
      Arrays.stream(ClimateClass.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, ClimateClass::getDisplayName))
    );
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
