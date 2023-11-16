package uk.gov.beis.els.categories.prorefrigeratedcabinets.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.validation.Valid;
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
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ClimateClass;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.service.ProRefrigeratedCabinetsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;
import uk.gov.beis.els.util.StreamUtils;

@Controller
@RequestMapping("/categories")
public class ProRefrigeratedCabinetsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Professional refrigerated storage cabinets";

  private final ProRefrigeratedCabinetsService proRefrigeratedCabinets;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public ProRefrigeratedCabinetsController(ProRefrigeratedCabinetsService proRefrigeratedCabinets,
                                           BreadcrumbService breadcrumbService,
                                           InternetLabelService internetLabelService,
                                           DocumentRendererService documentRendererService) {
    this.proRefrigeratedCabinets = proRefrigeratedCabinets;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/professional-refrigerated-storage-cabinets")
  public ModelAndView renderProfessionalRefrigeratedStorageCabinetsForm(@ModelAttribute("form") ProRefrigeratedCabinetsForm form) {
    return getProfessionalRefrigeratedStorageCabinetsForm(Collections.emptyList());
  }

  @PostMapping("/professional-refrigerated-storage-cabinets")
  @ResponseBody
  public Object handleProfessionalRefrigeratedStorageCabinetsFormSubmit(@Valid @ModelAttribute("form") ProRefrigeratedCabinetsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getProfessionalRefrigeratedStorageCabinetsForm(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processResponse(proRefrigeratedCabinets.generateHtml(form, ProRefrigeratedCabinetsService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/professional-refrigerated-storage-cabinets", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelProfessionalRefrigeratedStorageCabinetsFormSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") ProRefrigeratedCabinetsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getProfessionalRefrigeratedStorageCabinetsForm(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), ProRefrigeratedCabinetsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.PRO_REFRIGERATED_CABINETS));
    }
  }

  private ModelAndView getProfessionalRefrigeratedStorageCabinetsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/professional-refrigerated-storage-cabinets/professionalRefrigeratedStorageCabinets");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(
        ProRefrigeratedCabinetsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange()));
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
