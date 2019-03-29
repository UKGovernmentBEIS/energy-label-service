package uk.co.fivium.els.categories.localspaceheaters.controller;

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
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.internetlabelling.service.InternetLabelService;
import uk.co.fivium.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.co.fivium.els.categories.localspaceheaters.service.LocalSpaceHeatersService;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.service.ResponseService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
@RequestMapping("/categories")
public class LocalSpaceHeatersController {

  private final LocalSpaceHeatersService localSpaceHeatersService;
  private final BreadcrumbService breadcrumbService;
  private final InternetLabelService internetLabelService;
  private final ResponseService responseService;

  @Autowired
  public LocalSpaceHeatersController(LocalSpaceHeatersService localSpaceHeatersService,
                                     BreadcrumbService breadcrumbService,
                                     InternetLabelService internetLabelService,
                                     ResponseService responseService) {
    this.localSpaceHeatersService = localSpaceHeatersService;
    this.breadcrumbService = breadcrumbService;
    this.internetLabelService = internetLabelService;
    this.responseService = responseService;
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
      return responseService.processPdfResponse(localSpaceHeatersService.generateHtml(form));
    }
  }

  @PostMapping(value = "/local-space-heaters", params = "mode=INTERNET")
  @ResponseBody
  public Object handleInternetLabelLocalSpaceHeatersSubmit(@Validated(InternetLabellingGroup.class) @ModelAttribute("form") LocalSpaceHeatersForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView(bindingResult.getFieldErrors());
    } else {
      return responseService.processImageResponse(internetLabelService.generateInternetLabelHtml(form, form.getEfficiencyRating(), LocalSpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.LOCAL_SPACE_HEATERS));
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
