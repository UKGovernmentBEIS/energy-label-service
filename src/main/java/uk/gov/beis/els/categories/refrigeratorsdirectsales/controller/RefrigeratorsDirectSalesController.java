package uk.gov.beis.els.categories.refrigeratorsdirectsales.controller;

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
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.BeverageCoolersForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.DisplayCabinetsForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.IceCreamFreezersForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.RefrigeratorsDirectSalesCategory;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.VendingMachinesForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;
import uk.gov.beis.els.controller.CategoryController;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/refrigerators-direct-sales")
public class RefrigeratorsDirectSalesController extends CategoryController {

  private static final String BREADCRUMB_STAGE_TEXT = "Refrigerators with a direct sales function";

  private final RefrigeratorsDirectSalesService refrigeratorsDirectSales;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RefrigeratorsDirectSalesController(RefrigeratorsDirectSalesService refrigeratorsDirectSales,
                                            BreadcrumbService breadcrumbService,
                                            InternetLabelService internetLabelService,
                                            DocumentRendererService documentRendererService) {
    super(BREADCRUMB_STAGE_TEXT, breadcrumbService, RefrigeratorsDirectSalesCategory.GET, RefrigeratorsDirectSalesController.class);
    this.refrigeratorsDirectSales = refrigeratorsDirectSales;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/ice-cream-freezers")
  public ModelAndView renderIceCreamFreezers(@ModelAttribute("form") IceCreamFreezersForm form) {
    return getIceCreamFreezers(Collections.emptyList());
  }

  @PostMapping("/ice-cream-freezers")
  @ResponseBody
  public Object handleIceCreamFreezersSubmit(@Valid @ModelAttribute("form") IceCreamFreezersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getIceCreamFreezers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(refrigeratorsDirectSales.generateHtml(form, RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/ice-cream-freezers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelIceCreamFreezersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") IceCreamFreezersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getIceCreamFreezers(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.ICE_CREAM_FREEZERS));
    }
  }

  @GetMapping("/beverage-coolers")
  public ModelAndView renderBeverageCoolers(@ModelAttribute("form") BeverageCoolersForm form) {
    return getBeverageCoolers(Collections.emptyList());
  }

  @PostMapping("/beverage-coolers")
  @ResponseBody
  public Object handleBeverageCoolersSubmit(@Valid @ModelAttribute("form") BeverageCoolersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBeverageCoolers(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(refrigeratorsDirectSales.generateHtml(form, RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/beverage-coolers", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelBeverageCoolersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") BeverageCoolersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getBeverageCoolers(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.BEVERAGE_COOLERS));
    }
  }

  @GetMapping("/vending-machines")
  public ModelAndView renderVendingMachines(@ModelAttribute("form") VendingMachinesForm form) {
    return getVendingMachines(Collections.emptyList());
  }

  @PostMapping("/vending-machines")
  @ResponseBody
  public Object handleVendingMachinesSubmit(@Valid @ModelAttribute("form") VendingMachinesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getVendingMachines(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(refrigeratorsDirectSales.generateHtml(form, RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/vending-machines", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelVendingMachinesSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") VendingMachinesForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getVendingMachines(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.VENDING_MACHINES));
    }
  }

  @GetMapping("/display-cabinets")
  public ModelAndView renderDisplayCabinets(@ModelAttribute("form") DisplayCabinetsForm form) {
    return getDisplayCabinets(Collections.emptyList());
  }

  @PostMapping("/display-cabinets")
  @ResponseBody
  public Object handleDisplayCabinetsSubmit(@Valid @ModelAttribute("form") DisplayCabinetsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getDisplayCabinets(bindingResult.getFieldErrors());
    }
    else {
      return documentRendererService.processPdfResponse(refrigeratorsDirectSales.generateHtml(form, RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT));
    }
  }

  @PostMapping(value = "/display-cabinets", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelDisplayCabinetsSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") DisplayCabinetsForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getDisplayCabinets(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.DISPLAY_CABINETS));
    }
  }

  private ModelAndView getIceCreamFreezers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/refrigerators-direct-sales/iceCreamFreezers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderIceCreamFreezers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Ice cream freezers");
    return modelAndView;
  }

  private ModelAndView getBeverageCoolers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/refrigerators-direct-sales/beverageCoolers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderBeverageCoolers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Beverage coolers");
    return modelAndView;
  }

  private ModelAndView getVendingMachines(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/refrigerators-direct-sales/vendingMachines");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderVendingMachines(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Refrigerated vending machines");
    return modelAndView;
  }

  private ModelAndView getDisplayCabinets(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/refrigerators-direct-sales/displayCabinets");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(RefrigeratorsDirectSalesController.class).renderDisplayCabinets(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Supermarket refrigerator/freezer cabinets or gelato-scooping cabinets");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    modelAndView.addObject("showRescaledInternetLabelGuidance", true);
    super.addCommonProductGuidance(modelAndView);
    breadcrumbService.addBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT, ReverseRouter.route(on(
            RefrigeratorsDirectSalesController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
  }
}
