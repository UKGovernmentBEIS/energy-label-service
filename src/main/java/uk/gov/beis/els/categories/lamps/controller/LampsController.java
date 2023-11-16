package uk.gov.beis.els.categories.lamps.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import jakarta.validation.Valid;
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
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.lamps.model.LampsCategory;
import uk.gov.beis.els.categories.lamps.model.LampsForm;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.gov.beis.els.categories.lamps.model.LampsFormPackagingArrow;
import uk.gov.beis.els.categories.lamps.model.LightSourceArrowOrientation;
import uk.gov.beis.els.categories.lamps.model.TemplateColour;
import uk.gov.beis.els.categories.lamps.model.TemplateSize;
import uk.gov.beis.els.categories.lamps.service.LampsService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;
import uk.gov.beis.els.util.StreamUtils;

//TODO cut down on duplication here. All labels are essentially the same
@Controller
@RequestMapping("/categories/lamps")
public class LampsController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Lamps and light sources";

  private final LampsService lampsService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public LampsController(LampsService lampsService, BreadcrumbService breadcrumbService, InternetLabelService internetLabelService,
                         DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, LampsCategory.GET, LampsController.class);
    this.lampsService = lampsService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/lamps")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsForm form) {
    return getLamps(Collections.emptyList());
  }

  @PostMapping("/lamps")
  @ResponseBody
  public Object handleLampsSubmit(@Validated @ModelAttribute("form") LampsForm form, BindingResult bindingResult) {
    return doIfValid(form, bindingResult, (category -> documentRendererService.processResponse(lampsService.generateHtml(form, category))));
  }

  @PostMapping(value = "/lamps", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLampsSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LampsForm form, BindingResult bindingResult) {
    ControllerUtils.validateInternetLabelColour(form.getApplicableLegislation(), LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021, bindingResult);
    return doIfValid(form, bindingResult, (category -> documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), category, ProductMetadata.LAMPS_FULL))));
  }

  private Object doIfValid(LampsForm form, BindingResult bindingResult, Function<SelectableLegislationCategory, ResponseEntity> function) {
    ControllerUtils.validateRatingClassIfPopulated(form.getApplicableLegislation(), form.getEfficiencyRating(), LampsService.LEGISLATION_CATEGORIES, bindingResult);
    if (bindingResult.hasErrors()) {
      return getLamps(bindingResult.getFieldErrors());
    } else {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(form.getApplicableLegislation(), LampsService.LEGISLATION_CATEGORIES);
      return function.apply(category);
    }
  }

  @GetMapping("/lamps-excluding-name-model")
  public ModelAndView renderLampsExNameModel(@ModelAttribute("form") LampsFormNoSupplierModel form) {
    return getLampsExNameModel(Collections.emptyList());
  }

  @PostMapping("/lamps-excluding-name-model")
  @ResponseBody
  public Object handleLampsExNameModelSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModel form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModel(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processResponse(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
    }
  }

  @PostMapping(value = "/lamps-excluding-name-model", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLampsExNameModelSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LampsFormNoSupplierModel form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModel(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021, ProductMetadata.LAMPS_RATING_CONSUMPTION));
    }
  }

  @GetMapping("/lamps-excluding-name-model-consumption")
  public ModelAndView renderLampsExNameModelConsumption(@ModelAttribute("form") LampsFormNoSupplierModelConsumption form) {
    return getLampsExNameModelConsumption(Collections.emptyList());
  }

  @PostMapping("/lamps-excluding-name-model-consumption")
  @ResponseBody
  public Object handleLampsExNameModelConsumptionSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModelConsumption form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModelConsumption(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processResponse(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
    }
  }

  @PostMapping(value = "/lamps-excluding-name-model-consumption", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLampsExNameModelConsumptionSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LampsFormNoSupplierModelConsumption form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModelConsumption(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processInternetLabelResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021, ProductMetadata.LAMPS_RATING));
    }
  }

  @GetMapping("/lamps-packaging-arrow")
  public ModelAndView renderLampsPackagingArrow(@ModelAttribute("form") LampsFormPackagingArrow form) {
    return getLampsPackagingArrow(Collections.emptyList());
  }

  @PostMapping("/lamps-packaging-arrow")
  @ResponseBody
  public Object handleLampsPackagingArrowSubmit(@Valid @ModelAttribute("form") LampsFormPackagingArrow form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getLampsPackagingArrow(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processResponse(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021));
    }
  }

  private ModelAndView getLamps(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lamps");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).handleLampsSubmit(null, ReverseRouter.emptyBindingResult())));
    modelAndView.addObject("efficiencyRating", ControllerUtils.combinedLegislationCategoryRangesToSelectionMap(LampsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("legislationCategories", ControllerUtils.legislationCategorySelection(LampsService.LEGISLATION_CATEGORIES));
    modelAndView.addObject("templateSize",
        Arrays.stream(TemplateSize.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, TemplateSize::getDisplayName))
    );
    ControllerUtils.addShowRescaledInternetLabelGuidance(modelAndView);
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with supplier's name, model identification code, rating and energy consumption");
    return modelAndView;
  }

  private ModelAndView getLampsExNameModel(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModel");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null)));
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021.getPrimaryRatingRange()));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with energy rating and weighted energy consumption only");
    return modelAndView;
  }

  private ModelAndView getLampsExNameModelConsumption(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModelConsumption");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null)));
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021.getPrimaryRatingRange()));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with energy rating only");
    return modelAndView;
  }

  private ModelAndView getLampsPackagingArrow(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsPackagingArrow");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLampsPackagingArrow(null)));
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021.getPrimaryRatingRange()));
    modelAndView.addObject("labelOrientationOptions",
        Arrays.stream(LightSourceArrowOrientation.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, LightSourceArrowOrientation::getDisplayName))
    );
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Arrow for packaging");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("templateColour",
        Arrays.stream(TemplateColour.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, TemplateColour::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        LampsController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }
}
