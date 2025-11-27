package uk.gov.beis.els.categories.tumbledryers.controller;


import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.categories.tumbledryers.model.TumbleDryerLabelTypeForm;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories/tumble-dryers")
public class TumbleDryerTypeController {
  static final String BREADCRUMB_STAGE_TEXT = "Tumble dryers";
  
  private final BreadcrumbService breadcrumbService;

  public TumbleDryerTypeController(BreadcrumbService breadcrumbService) {
    this.breadcrumbService = breadcrumbService;
  }
  
  @GetMapping
  public ModelAndView renderTumbleDryerTypeForm(@ModelAttribute("form") TumbleDryerLabelTypeForm form) {
    return getTypeModelAndView(List.of());
  }

  @PostMapping
  public ModelAndView handleTumbleDryerTypeFormSubmit(@ModelAttribute("form") TumbleDryerLabelTypeForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.labelType())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "labelType", "labelType.required", "Select which type of label you need to create");
      return getTypeModelAndView(bindingResult.getFieldErrors());
    }
    
    if (form.labelType().equals(TumbleDryerLabelType.RESCALED.name())) {
      return ReverseRouter.redirect(on(RescaledTumbleDryersController.class).renderRescaledTumbleDryerForm(null));
    } else {
      return ReverseRouter.redirect(on(TumbleDryersController.class).renderCategories(null));
    }
  }

  private ModelAndView getTypeModelAndView(List<FieldError> errors) {
    var modelAndView = new ModelAndView("categories/tumble-dryers/tumbleDryerLabelType");
    
    var labelTypes = new LinkedHashMap<String, String>();
    labelTypes.put(TumbleDryerLabelType.RESCALED.name(), TumbleDryerLabelType.RESCALED.getDisplayText());
    labelTypes.put(TumbleDryerLabelType.OLD.name(), TumbleDryerLabelType.OLD.getDisplayText());
    
    modelAndView.addObject("labelTypes", labelTypes);
    
    ControllerUtils.addErrorSummary(modelAndView, errors);
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(TumbleDryerTypeController.class).handleTumbleDryerTypeFormSubmit(null, null)));
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, BREADCRUMB_STAGE_TEXT);
    
    return modelAndView;
  }
  
  public enum TumbleDryerLabelType{
    RESCALED("A rescaled energy label with an energy rating from from A to G (the “easement label”). The supply of this label is covered by the enforcement easement."),
    OLD("A label with an energy rating from A+++ to D, under assimilated Commission Delegated Regulation (EU) No 392/2012 (the “2012 label”).");
    
    private final String displayText;
    TumbleDryerLabelType(String displayText) {
      this.displayText = displayText;
    }
    
    public String getDisplayText() {
      return displayText;
    }
  }
  

}
