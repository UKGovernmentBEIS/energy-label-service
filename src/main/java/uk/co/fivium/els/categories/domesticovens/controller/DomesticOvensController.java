package uk.co.fivium.els.categories.domesticovens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvenCategory;
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvensForm;
import uk.co.fivium.els.categories.domesticovens.model.GasOvensForm;
import uk.co.fivium.els.categories.domesticovens.service.DomesticOvensService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.RatingClassRange;
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
@RequestMapping("/categories/domestic-ovens")
public class DomesticOvensController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Domestic ovens";

  private final PdfRenderer pdfRenderer;
  private final DomesticOvensService domesticOvensService;
  private final BreadcrumbService breadcrumbService;


  @Autowired
  public DomesticOvensController(PdfRenderer pdfRenderer, DomesticOvensService domesticOvensService, BreadcrumbService breadcrumbService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, DomesticOvenCategory.GET, DomesticOvensController.class);
    this.pdfRenderer = pdfRenderer;
    this.domesticOvensService = domesticOvensService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/electric-ovens")
  public ModelAndView renderElectricOvens(@ModelAttribute("form") DomesticOvensForm form) {
    return getElectricOvens(Collections.emptyList());
  }

  @PostMapping("/electric-ovens")
  @ResponseBody
  public Object handleElectricOvensSubmit(@Valid @ModelAttribute("form") DomesticOvensForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getElectricOvens(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(domesticOvensService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "domestic-ovens-label.pdf");
    }
  }

  @GetMapping("/gas-ovens")
  public ModelAndView renderGasOvens(@ModelAttribute("form") GasOvensForm form) {
    return getGasOvens(Collections.emptyList());
  }

  @PostMapping("/gas-ovens")
  @ResponseBody
  public Object handleGasOvensSubmit(@Valid @ModelAttribute("form") GasOvensForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getGasOvens(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(domesticOvensService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "domestic-ovens-label.pdf");
    }
  }

  private ModelAndView getElectricOvens(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/domestic-ovens/electricOvens");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(DomesticOvensController.class).renderElectricOvens(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Electric ovens");
    return modelAndView;
  }

  private ModelAndView getGasOvens(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/domestic-ovens/gasOvens");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(DomesticOvensController.class).renderGasOvens(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Gas ovens");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = DomesticOvensService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        DomesticOvensController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
