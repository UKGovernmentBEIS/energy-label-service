package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/water-heaters")
@Tag(name = "Water heaters", description = "Generate labels for water heaters")
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
      summary = "Conventional water heater: energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/conventional-water-heaters/energy-label")
  public Object conventionalWaterHeater(@RequestBody @Valid ConventionalWaterHeaterApiForm form) {
    return documentRendererService.processApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toConventionWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Conventional water heater: arrow image")
  @PostMapping("/conventional-water-heaters/arrow-image")
  public Object conventionalWaterHeaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_CONVENTIONAL)
    );
  }

  @Operation(
      summary = "Heat pump water heater: energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/heat-pump-water-heaters/energy-label")
  public Object heatPumpWaterHeater(@RequestBody @Valid HeatPumpWaterHeatersApiForm form) {
    return documentRendererService.processApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toHeatPumpWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Heat pump water heater: arrow image")
  @PostMapping("/heat-pump-water-heaters/arrow-image")
  public Object heatPumpWaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_HEAT_PUMP)
    );
  }

  @Operation(
      summary = "Solar water heater: energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/solar-water-heaters/energy-label")
  public Object solarWaterHeater(@RequestBody @Valid SolarWaterHeatersApiForm form) {
    return documentRendererService.processApiResponse(waterHeatersService.generateHtml(
        waterHeatersService.toSolarWaterHeatersForm(form), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Solar water heater: arrow image")
  @PostMapping("/solar-water-heaters/arrow-image")
  public Object solarWaterHeaterInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_SOLAR)
    );
  }

  @Operation(
      summary = "Hot water storage tank: energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/hot-water-storage-tanks/energy-label")
  public Object hotWaterStorageTank(@RequestBody @Valid HotWaterStorageTanksForm form) {
    return documentRendererService.processApiResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Hot water storage tank: arrow image")
  @PostMapping("/hot-water-storage-tanks/arrow-image")
  public Object hotWaterStorageTankInternetLabel(@Valid @RequestBody WaterHeatersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.WATER_HEATERS_STORAGE_TANKS)
    );
  }

  @Operation(
      summary = "Package of a water heater and solar device: energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed."
  )
  @PostMapping("/packages-of-water-heater-and-solar-device/energy-label")
  public Object waterHeaterSolarDevicePackage(@RequestBody @Valid WaterSolarPackagesForm form) {
    return documentRendererService.processApiResponse(waterHeatersService.generateHtml(form, WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES));
  }

  @Operation(summary = "Package of a water heater and solar device: arrow image")
  @PostMapping("/packages-of-water-heater-and-solar-device/arrow-image")
  public Object waterHeaterSolarDevicePackageInternetLabel(@Valid @RequestBody WaterSolarPackageInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES, ProductMetadata.WATER_HEATERS_PACKAGE)
    );
  }

  @Operation(summary = "Packages of water heater and solar device: energy label calculator")
  @PostMapping("/packages-of-water-heater-and-solar-device/calculate/energy-label")
  public Object waterHeaterSolarDevicePackageCalculator(@RequestBody @Valid WaterSolarPackagesCalculatorForm form) {
    return documentRendererService.processApiResponse(
        waterHeatersService.generateHtml(waterHeatersService.toWaterSolarPackagesForm(form),
            WaterHeatersService.LEGISLATION_CATEGORY_SOLAR_PACKAGES));
  }

  @Operation(summary = "Packages of water heater and solar device: fiche")
  @PostMapping("/packages-of-water-heater-and-solar-device/calculate/fiche")
  public Object waterHeaterSolarDevicePackageFiche(@RequestBody @Valid WaterSolarPackagesCalculatorForm form) {
    return documentRendererService.processApiResponse(
        waterHeatersService.generateFicheHtml(form),
        GoogleAnalyticsEventCategory.FICHE_API
    );
  }
}
