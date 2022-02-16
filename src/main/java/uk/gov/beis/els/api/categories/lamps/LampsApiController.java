package uk.gov.beis.els.api.categories.lamps;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.gov.beis.els.categories.lamps.service.LampsService;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/lamps")
@Tag(name = "Lamps and light sources")
public class LampsApiController {

  private final LampsService lampsService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public LampsApiController(LampsService lampsService,
                            InternetLabelService internetLabelService,
                            DocumentRendererService documentRendererService) {
    this.lampsService = lampsService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for lamps with energy rating only",
      description = "The label should be at least 36mm x 62mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white."
  )
  @PostMapping("/energy-rating-only/energy-label")
  public Object lampsExNameModelConsumption(@RequestBody @Valid LampsFormNoSupplierModelConsumption form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
  }

  @Operation(
      summary = "Create an energy label for lamps with energy rating and consumption only",
      description = "The label should be at least 36mm x 68mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white."
  )
  @PostMapping("/energy-rating-and-consumption-only/energy-label")
  public Object LampsExNameModel(@RequestBody @Valid LampsFormNoSupplierModel form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
  }
}
