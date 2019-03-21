package uk.co.fivium.els.categories.localspaceheaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.co.fivium.els.categories.localspaceheaters.service.LocalSpaceHeatersService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Controller
@RequestMapping("/categories")
public class LocalSpaceHeatersController {

  private final PdfRenderer pdfRenderer;
  private final LocalSpaceHeatersService localSpaceHeatersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public LocalSpaceHeatersController(PdfRenderer pdfRenderer,
                                     LocalSpaceHeatersService localSpaceHeatersService,
                                     BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.localSpaceHeatersService = localSpaceHeatersService;
    this.breadcrumbService = breadcrumbService;
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
      Resource pdf = pdfRenderer.render(localSpaceHeatersService.generateHtml(form));
      return ControllerUtils.serveResource(pdf, "local-space-heaters-label.pdf");
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
