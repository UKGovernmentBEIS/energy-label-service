package uk.co.fivium.els.categories.airconditioners.controller;

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
import uk.co.fivium.els.categories.airconditioners.model.AirConditionersCategory;
import uk.co.fivium.els.categories.airconditioners.model.CoolingDuctlessAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.ReversibleDuctlessAirConditionersForm;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.categories.airconditioners.service.AirConditionersService;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories/air-conditioners")
public class AirConditionersController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Air conditioners";

  private final PdfRenderer pdfRenderer;
  private final AirConditionersService airConditionersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public AirConditionersController(PdfRenderer pdfRenderer, AirConditionersService airConditionersService, BreadcrumbService breadcrumbService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, AirConditionersCategory.GET, AirConditionersController.class);
    this.pdfRenderer = pdfRenderer;
    this.airConditionersService = airConditionersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/non-duct/cooling-only-air-conditioners")
  public ModelAndView renderCoolingDuctlessAirConditioners(@ModelAttribute("form") CoolingDuctlessAirConditionersForm form) {
    return getCoolingDuctlessAirConditioners(Collections.emptyList());
  }

  @PostMapping("/non-duct/cooling-only-air-conditioners")
  @ResponseBody
  public Object handleCoolingDuctlessAirConditionersSubmit(@Valid @ModelAttribute("form") CoolingDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getCoolingDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }

  }

  @GetMapping("/non-duct/reversible-air-conditioners")
  public ModelAndView renderReversibleDuctlessAirConditioners(@ModelAttribute("form") ReversibleDuctlessAirConditionersForm form) {
    return getReversibleDuctlessAirConditioners(Collections.emptyList());
  }

  @PostMapping("/non-duct/reversible-air-conditioners")
  @ResponseBody
  public Object handleReversibleDuctlessAirConditionersSubmit(@Valid @ModelAttribute("form") ReversibleDuctlessAirConditionersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getReversibleDuctlessAirConditioners(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "air-conditioners-label.pdf");
    }

  }

  private ModelAndView getCoolingDuctlessAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/coolingDuctlessAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderCoolingDuctlessAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Cooling-only ductless air conditioners");
    return modelAndView;
  }

  private ModelAndView getReversibleDuctlessAirConditioners(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/air-conditioners/reversibleDuctlessAirConditioners");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(AirConditionersController.class).renderReversibleDuctlessAirConditioners(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Reversible ductless air conditioners");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList, String submitUrl) {
    RatingClassRange efficiencyRatingRange = AirConditionersService.LEGISLATION_CATEGORY_JAN2019.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
      AirConditionersController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }
}
