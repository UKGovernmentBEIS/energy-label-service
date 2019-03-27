package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.ukOrEuForm;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class UkOrEuController {

  private final BreadcrumbService breadcrumbService;

  @Value("${app.show_start_page}")
  private boolean startPageEnabled;

  @Autowired
  public UkOrEuController(BreadcrumbService breadcrumbService) {
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/uk-or-eu")
  public ModelAndView renderUkOrEu(@ModelAttribute("form") ukOrEuForm form) {
    return getUkOrEu(Collections.emptyList());
  }

  @GetMapping("/uk-or-eu/eu")
  public ModelAndView renderEu() {
    ModelAndView modelAndView = new ModelAndView("eu");
    modelAndView.addObject("backLinkUrl", ReverseRouter.route(on(UkOrEuController.class).renderUkOrEu(null)));
    return modelAndView;
  }

  @PostMapping("/uk-or-eu")
  @ResponseBody
  public Object handleUkOrEuSubmit(@Validated @ModelAttribute("form") ukOrEuForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getUkOrEu(bindingResult.getFieldErrors());
    } else if(bindingResult.getFieldValue("ukOrEuAnswer").equals("uk")) {
      return ReverseRouter.redirect(on(ProductCategoryController.class).renderCategories(null));
    } else {
      return ReverseRouter.redirect(on(UkOrEuController.class).renderEu());
    }
  }

  private ModelAndView getUkOrEu(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("ukOrEu");
    LinkedHashMap options = new LinkedHashMap();
    options.put("uk", "Just the UK");
    options.put("eu", "Just the EU");
    options.put("both", "Both the UK and the EU");
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("title", "Are you selling this item in the UK or the EU?");
    modelAndView.addObject("options", options);
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(UkOrEuController.class).renderUkOrEu(null)));
    return modelAndView;
  }
}