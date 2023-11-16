package uk.gov.beis.els.api.categories.tumbledryers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.gov.beis.els.categories.tumbledryers.model.TumbleDryersForm;
import uk.gov.beis.els.categories.tumbledryers.service.TumbleDryersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/tumble-dryers")
@Tag(name = "Tumble dryers", description = "Generate labels for tumble dryers")
public class TumbleDryerApiController {

  private final TumbleDryersService tumbleDryersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public TumbleDryerApiController(
      TumbleDryersService tumbleDryersService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.tumbleDryersService = tumbleDryersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Air vented tumble dryers: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed."
  )
  @PostMapping("/air-vented-tumble-dryers/energy-label")
  public Object airVentedTumbleDryer(@RequestBody @Valid TumbleDryersForm form) {
    return documentRendererService.processApiResponse(tumbleDryersService.generateHtmlAirVented(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Air vented tumble dryers: arrow image")
  @PostMapping("/air-vented-tumble-dryers/arrow-image")
  public Object airVentedTumbleDryerInternetLabel(@Valid @RequestBody TumbleDryerInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_AIR_VENTED)
    );
  }

  @Operation(
      summary = "Gas fired tumble dryers: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed."
  )
  @PostMapping("/gas-fired-tumble-dryers/energy-label")
  public Object gasFiredTumbleDryer(@RequestBody @Valid TumbleDryersForm form) {
    return documentRendererService.processApiResponse(tumbleDryersService.generateHtmlGasFired(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Gas fired tumble dryers: arrow image")
  @PostMapping("/gas-fired-tumble-dryers/arrow-image")
  public Object gasFiredTumbleDryerInternetLabel(@Valid @RequestBody TumbleDryerInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_GAS_FIRED)
    );
  }

  @Operation(
      summary = "Condenser tumble dryers: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed."
  )
  @PostMapping("/condenser-tumble-dryers/energy-label")
  public Object condenserTumbleDryer(@RequestBody @Valid CondenserTumbleDryersForm form) {
    return documentRendererService.processApiResponse(tumbleDryersService.generateHtmlCondenser(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Condenser tumble dryers: arrow image")
  @PostMapping("/condenser-tumble-dryers/arrow-image")
  public Object condenserTumbleDryerInternetLabel(@Valid @RequestBody TumbleDryerInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), TumbleDryersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.TUMBLE_DRYERS_CONDENSER)
    );
  }
}
