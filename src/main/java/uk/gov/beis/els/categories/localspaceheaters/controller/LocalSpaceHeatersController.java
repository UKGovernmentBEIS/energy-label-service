package uk.gov.beis.els.categories.localspaceheaters.controller;

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
import uk.gov.beis.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.gov.beis.els.categories.localspaceheaters.service.LocalSpaceHeatersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.service.BreadcrumbService;
import uk.gov.beis.els.service.DocumentRendererService;
import uk.gov.beis.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class LocalSpaceHeatersController {

  private final LocalSpaceHeatersService localSpaceHeatersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public LocalSpaceHeatersController(LocalSpaceHeatersService localSpaceHeatersService,
                                     BreadcrumbService breadcrumbService,
                                     InternetLabelService internetLabelService,
                                     DocumentRendererService documentRendererService) {
    this.localSpaceHeatersService = localSpaceHeatersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @GetMapping("/local-space-heaters")
  public ModelAndView renderLocalSpaceHeaters(@ModelAttribute("form") LocalSpaceHeatersForm form) {
    return getModelAndView();
  }

  @PostMapping("/local-space-heaters")
  @ResponseBody
  public Object handleLocalSpaceHeatersSubmit(@Valid @ModelAttribute("form") LocalSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processPdfResponse(localSpaceHeatersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/local-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLocalSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LocalSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return documentRendererService.processImageResponse(internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), LocalSpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.LOCAL_SPACE_HEATERS));
    }
  }

  private ModelAndView getModelAndView() {
    return getModelAndView(Collections.emptyList());
  }

  private ModelAndView getModelAndView(List<FieldError> errorList) {
    RatingClassRange efficiencyRatingRange = LocalSpaceHeatersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();

    ModelAndView modelAndView = new ModelAndView("categories/local-space-heaters/localSpaceHeaters");
    modelAndView.addObject("efficiencyRating", ControllerUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(LocalSpaceHeatersController.class).renderLocalSpaceHeaters(null)));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Local space heaters");

    return modelAndView;
  }



}
