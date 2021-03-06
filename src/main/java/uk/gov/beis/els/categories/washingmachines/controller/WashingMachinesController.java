package uk.gov.beis.els.categories.washingmachines.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.validation.Valid;
import javax.validation.groups.Default;
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
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.model.RescaledInternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.washingmachines.model.WasherDryerForm;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesCategory;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/washing-machines/")
public class WashingMachinesController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Washing machines and washer-dryers";

  private final WashingMachinesService washingMachinesService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public WashingMachinesController(WashingMachinesService washingMachinesService,
                                   BreadcrumbService breadcrumbService,
                                   InternetLabelService internetLabelService,
                                   DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, WashingMachinesCategory.GET, WashingMachinesController.class);
    this.washingMachinesService = washingMachinesService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/washing-machines")
  public ModelAndView renderWashingMachines(@ModelAttribute("form") WashingMachinesForm form) {
    return getWashingMachinesModelAndView();
  }

  @PostMapping("/washing-machines")
  @ResponseBody
  public Object handleWashingMachinesSubmit(@Valid @ModelAttribute("form") WashingMachinesForm form, BindingResult bindingResult) {
    return doIfValidWashingMachineForm(form, bindingResult, (category -> documentRendererService.processPdfResponse(washingMachinesService.generateHtml(form, category))));
  }

  @PostMapping(value = "/washing-machines", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWashingMachinesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") WashingMachinesForm form, BindingResult bindingResult) {
    ControllerUtils.validateInternetLabelColour(form.getApplicableLegislation(), WashingMachinesService.LEGISLATION_CATEGORY_POST_MARCH_2021, bindingResult);
    return doIfValidWashingMachineForm(form, bindingResult, (category -> documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.WASHING_MACHINES))));
  }

  @GetMapping("/washer-dryer")
  public ModelAndView renderWasherDryer(@ModelAttribute("form") WasherDryerForm form) {
    return getWasherDryerModelAndView();
  }

  @PostMapping("/washer-dryer")
  @ResponseBody
  public Object handleWasherDryerSubmit(@Validated({Default.class, PostMarch2021Field.class}) @ModelAttribute("form") WasherDryerForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWasherDryerModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(washingMachinesService.generateHtml(form, WashingMachinesService.LEGISLATION_CATEGORY_POST_MARCH_2021));
    }
  }

  @PostMapping(value = "/washer-dryer", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelWashingMachinesSubmit(@Validated({InternetLabellingGroup.class, RescaledInternetLabellingGroup.class}) @ModelAttribute("form") WasherDryerForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getWasherDryerModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getCompleteCycleEfficiencyRating(), WashingMachinesService.LEGISLATION_CATEGORY_POST_MARCH_2021, ProductMetadata.WASHING_MACHINES_WASHER_DRYER));
    }
  }

  private Object doIfValidWashingMachineForm(WashingMachinesForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), WashingMachinesService.LEGISLATION_CATEGORIES, bindingResult);
    if (bindingResult.hasErrors()) {
      return getWashingMachinesModelAndView(bindingResult.getFieldErrors());
    } else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), WashingMachinesService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  private ModelAndView getWasherDryerModelAndView() {
    return getWasherDryerModelAndView(Collections.emptyList());
  }

  private ModelAndView getWasherDryerModelAndView(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/washing-machines/washerDryers");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(WashingMachinesService.LEGISLATION_CATEGORY_POST_MARCH_2021.getPrimaryRatingRange()));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(WashingMachinesController.class).handleWasherDryerSubmit(null, ReverseRouter.emptyBindingResult())));
    addCommonModelAttributes(modelAndView, errorList);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Washer-dryers");
    return modelAndView;
  }

  private ModelAndView getWashingMachinesModelAndView() {
    return getWashingMachinesModelAndView(Collections.emptyList());
  }

  private ModelAndView getWashingMachinesModelAndView(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/washing-machines/washingMachines");
    modelAndView.addObject("legislationCategories", ControllerUtils.legislationCategorySelection(WashingMachinesService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(WashingMachinesService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null)));
    addCommonModelAttributes(modelAndView, errorList);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Washing machines");
    return modelAndView;
  }

  private void addCommonModelAttributes(ModelAndView modelAndView, List<FieldError> errorList) {
    modelAndView.addObject("noiseClass", ControllerUtils.ratingRangeToSelectionMap(WashingMachinesService.NOISE_EMISSIONS_CLASS_RANGE));
    modelAndView.addObject("spinDryingEfficiencyRating", ControllerUtils.ratingRangeToSelectionMap(WashingMachinesService.SPIN_DRYING_CLASS_RANGE));
    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        WashingMachinesController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
