package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/water-heaters")
@Tag(name = "Water heaters")
public class WaterHeatersApiController {

  private final WaterHeatersService waterHeatersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public WaterHeatersApiController(
      WaterHeatersService waterHeatersService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.waterHeatersService = waterHeatersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for a conventional water heater",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/conventional-water-heaters/energy-label")
  public Object conventionalWaterHeater(@RequestBody @Valid ConventionalWaterHeaterApiForm form) {
    return documentRendererService.processPdfApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toConventionWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for a conventional water heater")
  @PostMapping("/conventional-water-heaters/arrow-image")
  public Object conventionalWaterHeaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_CONVENTIONAL)
    );
  }

  @Operation(
      summary = "Create an energy label for a heat pump water heater",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/heat-pump-water-heaters/energy-label")
  public Object heatPumpWaterHeater(@RequestBody @Valid HeatPumpWaterHeatersApiForm form) {
    return documentRendererService.processPdfApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toHeatPumpWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for a heat pump water heater")
  @PostMapping("/heat-pump-water-heaters/arrow-image")
  public Object heatPumpWaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_HEAT_PUMP)
    );
  }

  @Operation(
      summary = "Create an energy label for a solar water heater",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/solar-water-heaters/energy-label")
  public Object solarWaterHeater(@RequestBody @Valid SolarWaterHeatersApiForm form) {
    return documentRendererService.processPdfApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toSolarWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for a solar water heater")
  @PostMapping("/solar-water-heaters/arrow-image")
  public Object solarWaterHeaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_SOLAR)
    );
  }

  @Operation(
      summary = "Create an energy label for a hot water storage tank",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/hot-water-storage-tanks/energy-label")
  public Object hotWaterStorageTank(@RequestBody @Valid HotWaterStorageTanksForm form) {
    return documentRendererService.processPdfApiResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for a hot water storage tank")
  @PostMapping("/hot-water-storage-tanks/arrow-image")
  public Object hotWaterStorageTankInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_STORAGE_TANKS)
    );
  }

  @Operation(
      summary = "Create an energy label for a package of a water heater and solar device",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed."
  )
  @PostMapping("/packages-of-water-heater-and-solar-device/energy-label")
  public Object waterHeaterSolarDevicePackage(@RequestBody @Valid WaterSolarPackagesForm form) {
    return documentRendererService.processPdfApiResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES));
  }

  @Operation(summary = "Create an arrow image for a package of a water heater and solar device")
  @PostMapping("/packages-of-water-heater-and-solar-device/arrow-image")
  public Object waterHeaterSolarDevicePackageInternetLabel(@Valid @RequestBody WaterSolarPackageInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES, ProductMetadata.WATER_HEATERS_PACKAGE)
    );
  }
}
