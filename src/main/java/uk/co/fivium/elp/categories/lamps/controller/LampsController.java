package uk.co.fivium.elp.categories.lamps.controller;

import java.util.Arrays;
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
import uk.co.fivium.elp.categories.lamps.model.LampsForm;
import uk.co.fivium.elp.categories.lamps.model.LampsFormNoSupplierModel;
import uk.co.fivium.elp.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.co.fivium.elp.categories.lamps.model.TemplateType;
import uk.co.fivium.elp.categories.lamps.service.LampsService;
import uk.co.fivium.elp.model.RatingClassRange;
import uk.co.fivium.elp.renderer.PdfRenderer;
import uk.co.fivium.elp.util.ControllerUtils;
import uk.co.fivium.elp.util.StreamUtils;

@Controller
@RequestMapping("/categories")
public class LampsController {

  private final PdfRenderer pdfRenderer;
  private final LampsService lampsService;

  @Autowired
  public LampsController(PdfRenderer pdfRenderer, LampsService lampsService) {
    this.pdfRenderer = pdfRenderer;
    this.lampsService = lampsService;
  }

  @GetMapping("/lamps")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsForm form) {
    return getModelAndView("categories/lamps/lamps");
  }

  @PostMapping("/lamps")
  @ResponseBody
  public Object handleLampsSubmit(@Valid @ModelAttribute("form") LampsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getModelAndView("categories/lamps/lamps");
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsFormNoSupplierModel form) {
    return getModelAndView("categories/lamps/lampsExcludingNameModel");
  }

  @PostMapping("/lamps-excluding-name-model")
  @ResponseBody
  public Object handleLampsSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModel form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getModelAndView("categories/lamps/lampsExcludingNameModel");
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model-consumption")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsFormNoSupplierModelConsumption form) {
    return getModelAndView("categories/lamps/lampsExcludingNameModelConsumption");
  }

  @PostMapping("/lamps-excluding-name-model-consumption")
  @ResponseBody
  public Object handleLampsSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModelConsumption form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getModelAndView("categories/lamps/lampsExcludingNameModelConsumption");
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  private ModelAndView getModelAndView(String view) {
    RatingClassRange efficiencyRatingRange = LampsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();

    ModelAndView modelAndView = new ModelAndView(view);
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("templateType",
        Arrays.stream(TemplateType.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, TemplateType::getDisplayName))
    );

    return modelAndView;
  }

}
