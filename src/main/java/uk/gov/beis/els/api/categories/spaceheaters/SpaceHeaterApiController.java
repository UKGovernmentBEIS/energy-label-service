package uk.gov.beis.els.api.categories.spaceheaters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/space-heaters")
@Tag(name = "Space heaters")
public class SpaceHeaterApiController {

  private final SpaceHeatersService spaceHeatersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  public SpaceHeaterApiController(SpaceHeatersService spaceHeatersService,
                                  InternetLabelService internetLabelService,
                                  DocumentRendererService documentRendererService) {
    this.spaceHeatersService = spaceHeatersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Boiler space heater energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/boiler-space-heaters/energy-label")
  public Object boilerSpaceHeater(@RequestBody @Valid BoilerSpaceHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Boiler space heater arrow image")
  @PostMapping("/boiler-space-heaters/arrow-image")
  public Object boilerSpaceHeater(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_BOILER));
  }

  @Operation(
      summary = "Boiler combination heater energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/boiler-combination-heaters/energy-label")
  public Object boilerCombinationHeaters(@RequestBody @Valid BoilerCombinationHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Boiler combination heater arrow image")
  @PostMapping("/boiler-combination-heaters/arrow-image")
  public Object boilerCombinationHeatersInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_BOILER_COMBI));
  }

  @Operation(
      summary = "Cogeneration space heaters energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/cogeneration-space-heaters/energy-label")
  public Object cogenerationSpaceHeaters(@RequestBody @Valid CogenerationSpaceHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Cogeneration space heaters arrow image")
  @PostMapping("/cogeneration-space-heaters/arrow-image")
  public Object cogenerationSpaceHeatersInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_COGEN));
  }

  @Operation(
      summary = "Low-temperature heat pump space heaters energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/low-temperature-heat-pump-space-heaters/energy-label")
  public Object lowTempertatureHeatPumpSpaceHeater(@RequestBody @Valid LowTemperatureHeatPumpSpaceHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Low-temperature heat pump space heaters arrow image")
  @PostMapping("/low-temperature-heat-pump-space-heaters/arrow-image")
  public Object lowTempertatureHeatPumpSpaceHeaterInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_LOW_TEMP));
  }

  @Operation(
      summary = "Heat pump space heaters (except low-temperature heat pumps) energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/heat-pump-space-heaters/energy-label")
  public Object heatPumpSpaceHeater(@RequestBody @Valid HeatPumpSpaceHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Heat pump space heaters (except low-temperature heat pumps) arrow image")
  @PostMapping("/heat-pump-space-heaters/arrow-image")
  public Object heatPumpSpaceHeaterInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_HEAT_PUMP));
  }

  @Operation(
      summary = "Heat pump combination heaters energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/heat-pump-combination-heaters/energy-label")
  public Object heatPumpCombinationHeater(@RequestBody @Valid HeatPumpCombinationHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Heat pump combination heaters arrow image")
  @PostMapping("/heat-pump-combination-heaters/arrow-image")
  public Object heatPumpCombinationHeaterInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_HEAT_PUMP_COMBINATION));
  }

  @Operation(
      summary = "Packages of space heater, temperature control and solar device energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed."
  )
  @PostMapping("/package-space-heater/energy-label")
  public Object packageSpaceHeater(@RequestBody @Valid SpaceHeaterPackagesForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form));
  }

  @Operation(summary = "Packages of space heater, temperature control and solar device arrow image")
  @PostMapping("/package-space-heater/arrow-image")
  public Object packageSpaceHeaterInternetLabel(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_PACKAGE));
  }
}
