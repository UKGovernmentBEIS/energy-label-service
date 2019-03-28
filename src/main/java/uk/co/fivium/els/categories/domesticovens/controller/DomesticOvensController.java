package uk.co.fivium.els.categories.domesticovens.controller;

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
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvenCategory;
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvensForm;
import uk.co.fivium.els.categories.domesticovens.model.GasOvensForm;
import uk.co.fivium.els.categories.domesticovens.service.DomesticOvensService;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.controller.CategoryController;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.ResponseService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/domestic-ovens")
public class DomesticOvensController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Domestic ovens";

  private final DomesticOvensService domesticOvensService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final ResponseService responseService;

  @Autowired
  public DomesticOvensController(DomesticOvensService domesticOvensService,
                                 BreadcrumbService breadcrumbService,
                                 InternetLabelService internetLabelService,
                                 ResponseService responseService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, DomesticOvenCategory.GET, DomesticOvensController.class);
    this.domesticOvensService = domesticOvensService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.responseService = responseService;
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
      return responseService.processPdfResponse(domesticOvensService.generateHtml(form));
    }
  }

  @PostMapping(value = "/electric-ovens", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelElectricOvensSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") DomesticOvensForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getElectricOvens(bindingResult.getFieldErrors());
    }
    else {
      return responseService.processImageResponse(internetLabelService.generateInternetLabelHtml(form, form.getEfficiencyRating(), DomesticOvensService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.OVENS_ELECTRIC));
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
      return responseService.processPdfResponse(domesticOvensService.generateHtml(form));
    }
  }

  @PostMapping(value = "/gas-ovens", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelGasOvensSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") GasOvensForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getElectricOvens(bindingResult.getFieldErrors());
    }
    else {
      return responseService.processImageResponse(internetLabelService.generateInternetLabelHtml(form, form.getEfficiencyRating(), DomesticOvensService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.OVENS_GAS));
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
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
        DomesticOvensController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }

}
