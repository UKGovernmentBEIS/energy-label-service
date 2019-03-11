package uk.co.fivium.elp.categories.airconditioners.controller;

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
import uk.co.fivium.elp.categories.airconditioners.model.ReversibleAirConditionersForm;
import uk.co.fivium.elp.renderer.PdfRenderer;
import uk.co.fivium.elp.categories.airconditioners.service.ReversibleAirConditionerService;
import uk.co.fivium.elp.util.ControllerUtils;

@Controller
@RequestMapping("air-conditioners")
public class AirConditionersController {

  private final PdfRenderer pdfRenderer;
  private final ReversibleAirConditionerService reversibleAirConditionerService;

  @Autowired
  public AirConditionersController(PdfRenderer pdfRenderer,
                                   ReversibleAirConditionerService reversibleAirConditionerService) {
    this.pdfRenderer = pdfRenderer;
    this.reversibleAirConditionerService = reversibleAirConditionerService;
  }

  @GetMapping("/non-duct/reversible-air-conditioners")
  public ModelAndView renderAirConTest(@ModelAttribute("form") ReversibleAirConditionersForm form) {
    return new ModelAndView("air-conditioners/non-duct/reversible-air-conditioners");
  }

  @PostMapping("/non-duct/reversible-air-conditioners")
  @ResponseBody
  public Object handleAirConTestSubmit(@Valid @ModelAttribute("form") ReversibleAirConditionersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("air-conditioners/non-duct/reversible-air-conditioners");
    }
    else {
      Resource pdf = pdfRenderer.render(reversibleAirConditionerService.generateHtml(form, ReversibleAirConditionerService.LEGISLATION_CATEGORY_JAN2019));
      return ControllerUtils.serveResource(pdf, "reversible-air-conditioners.pdf");
    }

  }

}
