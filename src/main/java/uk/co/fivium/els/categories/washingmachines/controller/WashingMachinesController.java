package uk.co.fivium.els.categories.washingmachines.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.washingmachines.model.WashingMachinesForm;
import uk.co.fivium.els.categories.washingmachines.service.WashingMachinesService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories")
public class WashingMachinesController {

  private final PdfRenderer pdfRenderer;
  private final WashingMachinesService washingMachinesService;

  @Autowired
  public WashingMachinesController(PdfRenderer pdfRenderer,
                                   WashingMachinesService washingMachinesService) {
    this.pdfRenderer = pdfRenderer;
    this.washingMachinesService = washingMachinesService;
  }

  @GetMapping("/washing-machines")
  public ModelAndView renderWashingMachines(@ModelAttribute("form") WashingMachinesForm form) {
    return getModelAndView();
  }

  @PostMapping("/washing-machines")
  @ResponseBody
  public Object handleWashingMachinesSubmit(@Valid @ModelAttribute("form") WashingMachinesForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getModelAndView();
    } else {
      Resource pdf = pdfRenderer.render(washingMachinesService.generateHtml(form, WashingMachinesService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "washing-machines-label.pdf");
    }
  }

  private ModelAndView getModelAndView() {
    RatingClassRange efficiencyRatingRange = WashingMachinesService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    RatingClassRange spinEfficiencyRange = WashingMachinesService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();

    ModelAndView modelAndView = new ModelAndView("categories/washing-machines/washingMachines");
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("spinDryingEfficiencyRating", StreamUtils.ratingRangeToSelectionMap(spinEfficiencyRange));
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null)));

    return modelAndView;
  }

}
