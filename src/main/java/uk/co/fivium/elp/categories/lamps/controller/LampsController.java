package uk.co.fivium.elp.categories.lamps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.elp.categories.lamps.model.LampsForm;
import uk.co.fivium.elp.renderer.PdfRenderer;
import uk.co.fivium.elp.categories.airconditioners.service.ReversibleAirConditionerService;

@Controller
@RequestMapping("/categories")
public class LampsController {

  private final PdfRenderer pdfRenderer;
  private final ReversibleAirConditionerService reversibleAirConditionerService;

  @Autowired
  public LampsController(PdfRenderer pdfRenderer,
                                   ReversibleAirConditionerService reversibleAirConditionerService) {
    this.pdfRenderer = pdfRenderer;
    this.reversibleAirConditionerService = reversibleAirConditionerService;
  }

  @GetMapping("/lamps")
  public ModelAndView renderLampsAll(@ModelAttribute("form") LampsForm form) {
    return new ModelAndView("categories/lamps/lamps.ftl");
  }

//  @PostMapping("/non-duct/reversible-air-conditioners")
//  @ResponseBody
//  public Object handleAirConTestSubmit(@Valid @ModelAttribute("form") ReversibleAirConditionersForm form, BindingResult bindingResult) throws Exception {
//    if (bindingResult.hasErrors()) {
//      return new ModelAndView("air-conditioners/non-duct/reversible-air-conditioners");
//    }
//    else {
//      Resource pdf = pdfRenderer.render(reversibleAirConditionerService.resolve(form, ReversibleAirConditionerService.LEGISLATION_CATEGORY_JAN2019));
//
//      return ResponseEntity.ok()
//          .contentType(MediaType.APPLICATION_OCTET_STREAM)
//          .contentLength(pdf.contentLength())
//          .header(
//              HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "sample-energy-label.pdf"))
//          .body(pdf);
//    }
//
//  }

}
