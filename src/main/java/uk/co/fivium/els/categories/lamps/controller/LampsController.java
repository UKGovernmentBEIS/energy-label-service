package uk.co.fivium.els.categories.lamps.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
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
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.lamps.model.LampsCategory;
import uk.co.fivium.els.categories.lamps.model.LampsForm;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.co.fivium.els.categories.lamps.model.TemplateType;
import uk.co.fivium.els.categories.lamps.service.LampsService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/lamps")
public class LampsController {

  private static final String BREADCRUMB_STAGE_TEXT = "Lamps";

  private final PdfRenderer pdfRenderer;
  private final LampsService lampsService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public LampsController(PdfRenderer pdfRenderer, LampsService lampsService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.lampsService = lampsService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderLampSubCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getLampsSubCategory(Collections.emptyList());
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleLampSubCategoriesSubmit(@Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    return ControllerUtils.handleSubCategorySubmit(LampsCategory.GET, form, bindingResult, (this::getLampsSubCategory));
  }

  private ModelAndView getLampsSubCategory(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(LampsCategory.GET,
        errors,
        ReverseRouter.route(on(LampsController.class).handleLampSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
        BREADCRUMB_STAGE_TEXT,
        breadcrumbService
    );
  }

  @GetMapping("/lamps")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsForm form) {
    return getLamps(Collections.emptyList());
  }

  @PostMapping("/lamps")
  @ResponseBody
  public Object handleLampsSubmit(@Valid @ModelAttribute("form") LampsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLamps(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model")
  public ModelAndView renderLampsExNameModel(@ModelAttribute("form") LampsFormNoSupplierModel form) {
    return getLampsExNameModel(Collections.emptyList());
  }

  @PostMapping("/lamps-excluding-name-model")
  @ResponseBody
  public Object handleLampsExNameModelSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModel form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModel(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model-consumption")
  public ModelAndView renderLampsExNameModelConsumption(@ModelAttribute("form") LampsFormNoSupplierModelConsumption form) {
    return getLampsExNameModelConsumption(Collections.emptyList());
  }

  @PostMapping("/lamps-excluding-name-model-consumption")
  @ResponseBody
  public Object handleLampsExNameModelConsumptionSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModelConsumption form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModelConsumption(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  private ModelAndView getLamps(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lamps");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLamps(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with supplier's name, model identifier, rating and energy consumption");
    return modelAndView;
  }

  private ModelAndView getLampsExNameModel(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModel");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with energy rating and weighted energy consumption only");
    return modelAndView;
  }

  private ModelAndView getLampsExNameModelConsumption(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModelConsumption");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Label with energy rating only");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = LampsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("templateType",
        Arrays.stream(TemplateType.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, TemplateType::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(LampsController.class).renderLampSubCategories(null)));
  }

}
