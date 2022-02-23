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
import uk.gov.beis.els.categories.lamps.model.LampsFormPackagingArrow;
import uk.gov.beis.els.categories.lamps.service.LampsService;
import uk.gov.beis.els.model.ProductMetadata;
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
      summary = "Lamps with energy rating only: Energy label",
      description = "The label should be at least 36mm x 62mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white."
  )
  @PostMapping("/energy-rating-only/energy-label")
  public Object lampsExNameModelConsumption(@RequestBody @Valid LampsFormNoSupplierModelConsumption form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
  }

  @Operation(
      summary = "Lamps with energy rating and consumption only: Energy label",
      description = "The label should be at least 36mm x 68mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white."
  )
  @PostMapping("/energy-rating-and-consumption-only/energy-label")
  public Object lampsExNameModel(@RequestBody @Valid LampsFormNoSupplierModel form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021));
  }

  @Operation(
      summary = "Create an old style energy label including the energy rating, weighted energy consumption, supplier's name and model identification code",
      description = "If the product was first placed on the market on or after 1 October 2021, or hasn't been placed on the market yet, you must use the new rescaled energy label." +
          "\nProducts placed on the market before 1 October 2021 can continue to use the old style label until 31 March 2023." +
          "\nOld-style labels must usually be at least 36mm x 75mm when attached to packaging. You can scale down the label if no side of the packaging is large enough to contain the label, or if the label would cover more than 50% of the surface area of the largest side. You must only scale down the label enough to meet these conditions, and the label must never be less than 14.4mm x 30mm."
  )
  @PostMapping("/all-fields/old-style/energy-label")
  public Object lampsAllFieldsOldStyle(@RequestBody @Valid LampsPreSeptember2021ApiForm form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(lampsService.toStandardLampsForm(form),
            LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021)
    );
  }

  @Operation(
      summary = "Create a new style energy label including the energy rating, weighted energy consumption, supplier's name and model identification code",
      description = "New-style rescaled labels must be at least 36mm x 72mm, or 20mm x 54mm for the small version of the label."
  )
  @PostMapping("/all-fields/new-style/energy-label")
  public Object lampsAllFields(@RequestBody @Valid LampsPostSeptember2021ApiForm form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(lampsService.toStandardLampsForm(form),
            LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021)
    );
  }

  @Operation(summary = "Lamps and light sources: New style arrow image")
  @PostMapping("/new-style/arrow-image")
  public Object lampsNewStyleInternetLabel(@RequestBody @Valid LampsNewStyleInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021, ProductMetadata.LAMPS_FULL)
    );
  }

  @Operation(summary = "Lamps and light sources: Old style arrow image")
  @PostMapping("/old-style/arrow-image")
  public Object lampsOldStyleInternetLabel(@RequestBody @Valid LampsOldStyleInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021, ProductMetadata.LAMPS_FULL)
    );
  }

  @Operation(
      summary = "light source packaging: Energy rating arrow",
      description = "This arrow must only be used on products which include a new-style rescaled energy label. The arrow must be shown on the front of the packaging if the energy label isn't on the front. It must be clearly visible and legible. You don't need to include this arrow on the packaging if the energy label is on the front."
  )
  @PostMapping("/new-style/packaging-arrow")
  public Object packagingArrow(@RequestBody @Valid LampsFormPackagingArrow form) {
    return documentRendererService.processPdfApiResponse(
        lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021));
  }
}
