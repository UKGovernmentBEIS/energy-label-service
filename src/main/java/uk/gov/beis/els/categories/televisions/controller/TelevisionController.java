package uk.gov.beis.els.categories.televisions.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.model.RescaledInternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.categories.televisions.service.TelevisionsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class TelevisionController {

  private static final String BREADCRUMB_STAGE_TEXT = "Televisions and electronic displays";

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
  public Object handleTelevisionsFormSubmit(@Valid @ModelAttribute("form") TelevisionsForm form,
                                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getTelevisionsForm(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processResponse(televisionsService.generateHtml(form));
    }
  }

  @PostMapping(value = "/televisions", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelTelevisionsFormSubmit(
      @Validated({InternetLabellingGroup.class, RescaledInternetLabellingGroup.class}) @ModelAttribute("form") TelevisionsForm form,
      BindingResult bindingResult) {
    // Conditional fields in internet labels need to be explicitly validated as Spring validation happens against the InternetLabellingGroup only.
    ValidationUtils.rejectIfEmpty(bindingResult, "efficiencyRatingSdr", "efficiencyRatingSdr.invalid", "Select an energy efficiency class for SDR content");
    if (bindingResult.hasErrors()) {
      return getTelevisionsForm(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processInternetLabelResponse(
          internetLabelService.generateInternetLabel(form, form.getEfficiencyRatingSdr(),
              TelevisionsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TV));
    }
  }

  private ModelAndView getTelevisionsForm(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/televisions/televisions");
    modelAndView.addObject("efficiencyRatingSdr", ControllerUtils.ratingRangeToSelectionMap(
        TelevisionsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange()));
    modelAndView.addObject("efficiencyRatingHdr", ControllerUtils.ratingRangeToSelectionMap(
        TelevisionsService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange()));
    modelAndView.addObject("submitUrl", ReverseRouter.route(
        on(TelevisionController.class).handleTelevisionsFormSubmit(null, ReverseRouter.emptyBindingResult())));
    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    return modelAndView;
  }

}
