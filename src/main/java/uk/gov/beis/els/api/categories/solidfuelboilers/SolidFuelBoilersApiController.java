package uk.gov.beis.els.api.categories.solidfuelboilers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.solidfuelboilers.model.SolidFuelBoilerPackagesForm;
import uk.gov.beis.els.categories.solidfuelboilers.model.SolidFuelBoilersForm;
import uk.gov.beis.els.categories.solidfuelboilers.service.SolidFuelBoilersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/solid-fuel-boilers")
@Tag(name = "Solid fuel boilers")
public class SolidFuelBoilersApiController {

  private final SolidFuelBoilersService solidFuelBoilersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public SolidFuelBoilersApiController(
      SolidFuelBoilersService solidFuelBoilersService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.solidFuelBoilersService = solidFuelBoilersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for a solid fuel boiler",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/solid-fuel-boilers/energy-label")
  public Object solidFuelBoilers(@RequestBody @Valid SolidFuelBoilersForm form) {
    return documentRendererService.processPdfApiResponse(solidFuelBoilersService.generateHtml(form, SolidFuelBoilersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for a solid fuel boiler")
  @PostMapping("/solid-fuel-boilers/arrow-image")
  public Object solidFuelBoilersInternetLabel(@Valid @RequestBody SolidFuelBoilersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), SolidFuelBoilersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SOLID_FUEL_BOILER)
    );
  }

  @Operation(
      summary = "Create an energy label for a package of a solid fuel boiler, supplementary heaters, temperature controls and solar devices",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed."
  )
  @PostMapping("/package-solid-fuel-boiler/energy-label")
  public Object solidFuelBoilerPackages(@RequestBody @Valid SolidFuelBoilerPackagesForm form) {
    return documentRendererService.processPdfApiResponse(solidFuelBoilersService.generateHtml(form));
  }



  @Operation(summary = "Create an arrow image for a  a package of a solid fuel boiler, supplementary heaters, temperature controls and solar devices")
  @PostMapping("/package-solid-fuel-boiler/arrow-image")
  public Object solidFuelBoilerPackagesInternetLabel(@Valid @RequestBody SolidFuelBoilersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), SolidFuelBoilersService.LEGISLATION_CATEGORY_PACKAGES_CURRENT, ProductMetadata.SOLID_FUEL_BOILER_PACKAGE)
    );
  }
}
