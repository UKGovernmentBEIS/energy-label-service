package uk.gov.beis.els.categories.washingmachines.controller;

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
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class WashingMachinesController {

  private final WashingMachinesService washingMachinesService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public WashingMachinesController(WashingMachinesService washingMachinesService,
                                   BreadcrumbService breadcrumbService,
                                   InternetLabelService internetLabelService,
                                   DocumentRendererService documentRendererService) {
    this.washingMachinesService = washingMachinesService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/washing-machines")
  public ModelAndView renderWashingMachines(@ModelAttribute("form") WashingMachinesForm form) {
    return getModelAndView();
  }

  @PostMapping("/washing-machines")
  @ResponseBody
  public Object handleWashingMachinesSubmit(@Valid @ModelAttribute("form") WashingMachinesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(washingMachinesService.generateHtml(form, WashingMachinesService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/washing-machines", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWashingMachinesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") WashingMachinesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WashingMachinesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WASHING_MACHINES));
    }
  }

  private ModelAndView getModelAndView() {
    return getModelAndView(Collections.emptyList());
  }

  private ModelAndView getModelAndView(List<FieldError> errorList) {
    RatingClassRange efficiencyRatingRange = WashingMachinesService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    RatingClassRange spinEfficiencyRange = WashingMachinesService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();

    ModelAndView modelAndView = new ModelAndView("categories/washing-machines/washingMachines");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("spinDryingEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(spinEfficiencyRange));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null)));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Washing machines");

    return modelAndView;
  }



}
