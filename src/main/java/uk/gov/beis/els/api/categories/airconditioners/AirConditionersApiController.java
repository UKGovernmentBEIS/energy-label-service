package uk.gov.beis.els.api.categories.airconditioners;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.airconditioners.model.CoolingDuctedAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.model.CoolingDuctlessAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.model.HeatingDuctedAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.model.HeatingDuctlessAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.model.ReversibleDuctedAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.model.ReversibleDuctlessAirConditionersForm;
import uk.gov.beis.els.categories.airconditioners.service.AirConditionersService;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/air-conditioners")
@Tag(name = "Air conditioners", description = "Generate labels for air conditioners")
public class AirConditionersApiController {

  private final AirConditionersService airConditionersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public AirConditionersApiController(
      AirConditionersService airConditionersService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.airConditionersService = airConditionersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Cooling-only ductless air conditioners: energy label",
      description = "The label must be at least 100mm x 200mm when printed."
  )
  @PostMapping("/non-duct/cooling-only-air-conditioners/energy-label")
  public Object coolingOnlyDuctlessAirConditioners(@RequestBody @Valid CoolingDuctlessAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Cooling-only ductless air conditioners: arrow image")
  @PostMapping("/non-duct/cooling-only-air-conditioners/arrow-image")
  public Object coolingOnlyDuctlessInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_COOLING_ONLY_NON_DUCT)
    );
  }

  @Operation(
      summary = "Heating-only ductless air conditioners: energy label",
      description = "The label must be at least 100mm x 200mm when printed."
  )
  @PostMapping("/non-duct/heating-only-air-conditioners/energy-label")
  public Object heatingOnlyDuctlessAirConditioners(@RequestBody @Valid HeatingDuctlessAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Heating-only ductless air conditioners: arrow image")
  @PostMapping("/non-duct/heating-only-air-conditioners/arrow-image")
  public Object heatingOnlyDuctlessInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_HEATING_ONLY_NON_DUCT)
    );
  }

  @Operation(
      summary = "Reversible ductless air conditioners: energy label",
      description = "The label must be at least 120mm x 210mm when printed "
  )
  @PostMapping("/non-duct/reversible-air-conditioners/energy-label")
  public Object reversibleDuctlessAirConditioners(@RequestBody @Valid ReversibleDuctlessAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Reversible ductless air conditioners: arrow image")
  @PostMapping("/non-duct/reversible-air-conditioners/arrow-image")
  public Object reversibleDuctlessInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_REVERSIBLE_NON_DUCT)
    );
  }

  @Operation(
      summary = "Cooling-only single or double duct air conditioners: energy label",
      description = "The label must be at least 100mm x 200mm when printed."
  )
  @PostMapping("/single-or-double-duct/cooling-only-air-conditioners/energy-label")
  public Object coolingOnlyDuctedAirConditioners(@RequestBody @Valid CoolingDuctedAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Cooling-only single or double duct air conditioners: arrow image")
  @PostMapping("/single-or-double-duct/cooling-only-air-conditioners/arrow-image")
  public Object coolingOnlyDuctedInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_COOLING_ONLY_DUCT)
    );
  }

  @Operation(
      summary = "Heating-only single or double duct air conditioners: energy label",
      description = "The label must be at least 100mm x 200mm when printed."
  )
  @PostMapping("/single-or-double-duct/heating-only-air-conditioners/energy-label")
  public Object heatingOnlyDuctedAirConditioners(@RequestBody @Valid HeatingDuctedAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Heating-only single or double duct air conditioners: arrow image")
  @PostMapping("/single-or-double-duct/heating-only-air-conditioners/arrow-image")
  public Object heatingOnlyDuctedInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_HEATING_ONLY_DUCT)
    );
  }

  @Operation(
      summary = "Reversible single or double duct air conditioners: energy label",
      description = "The label must be at least 100mm x 200mm when printed."
  )
  @PostMapping("/single-or-double-duct/reversible-air-conditioners/energy-label")
  public Object reversibleDuctedAirConditioners(@RequestBody @Valid ReversibleDuctedAirConditionersForm form) {
    return documentRendererService.processApiResponse(airConditionersService.generateHtml(form, AirConditionersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Reversible single or double duct air conditioners: arrow image")
  @PostMapping("/single-or-double-duct/reversible-air-conditioners/arrow-image")
  public Object reversibleDuctedInternetLabel(@Valid @RequestBody AirConditionersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), AirConditionersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.AC_REVERSIBLE_DUCT)
    );
  }
}
