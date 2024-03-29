package uk.gov.beis.els.api.categories.ventilationunits;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.ventilationunits.model.VentilationUnitsForm;
import uk.gov.beis.els.categories.ventilationunits.service.VentilationUnitsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/ventilation-units")
@Tag(name = "Ventilation units", description = "Generate labels for ventilation units")
public class VentilationUnitsApiController {

  private final VentilationUnitsService ventilationUnitsService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public VentilationUnitsApiController(
      VentilationUnitsService ventilationUnitsService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.ventilationUnitsService = ventilationUnitsService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Unidirectional ventilation units: energy label",
      description = "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 75mm x 150mm when printed."
  )
  @PostMapping("/unidirectional-ventilation-units/energy-label")
  public Object unidirectionalVentilationUnit(@RequestBody @Valid VentilationUnitsForm form) {
    return documentRendererService.processApiResponse(ventilationUnitsService.generateHtmlUnidirectional(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Unidirectional ventilation units: arrow image")
  @PostMapping("/unidirectional-ventilation-units/arrow-image")
  public Object unidirectionalVentilationUnitInternetLabel(@Valid @RequestBody VentilationUnitsInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.VENTILATION_UNITS_UNIDIRECTIONAL)
    );
  }

  @Operation(
      summary = "Bidirectional ventilation units: energy label",
      description = "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 75mm x 150mm when printed. "
  )
  @PostMapping("/bidirectional-ventilation-units/energy-label")
  public Object bidirectionalVentilationUnit(@RequestBody @Valid VentilationUnitsForm form) {
    return documentRendererService.processApiResponse(ventilationUnitsService.generateHtmlBidirectional(form, VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Bidirectional ventilation units: arrow image")
  @PostMapping("/bidirectional-ventilation-units/arrow-image")
  public Object bidirectionalVentilationUnitInternetLabel(@Valid @RequestBody VentilationUnitsInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), VentilationUnitsService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.VENTILATION_UNITS_BIDIRECTIONAL)
    );
  }
}
