package uk.gov.beis.els.api.categories.washingmachines;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.washingmachines.model.WasherDryerForm;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/washing-machines")
@Tag(name = "Washing machines and washer-dryers")
public class WashingMachinesApiController {

  private final WashingMachinesService washingMachinesService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public WashingMachinesApiController(
      WashingMachinesService washingMachinesService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.washingMachinesService = washingMachinesService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for washing machines",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in washing machine it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/washing-machines/energy-label")
  public Object washingMachines(@RequestBody @Valid WashingMachinesForm form) {
    return documentRendererService.processPdfApiResponse(washingMachinesService.generateHtml(form));
  }

  @Operation(summary = "Create an arrow image for washing machines")
  @PostMapping("/washing-machines/arrow-image")
  public Object washingMachinesInternetLabel(@RequestBody @Valid WashingMachinesInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            WashingMachinesService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.WASHING_MACHINES
        ));
  }

  @Operation(
      summary = "Create an energy label for washer-dryers",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in washer-dryer it doesn't have to be attached to the product, but it must still be easy to see. It must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/washer-dryer/energy-label")
  public Object washerDryer(@RequestBody @Valid WasherDryerForm form) {
    return documentRendererService.processPdfApiResponse(washingMachinesService.generateHtml(form));
  }

  @Operation(summary = "Create an arrow image for washer-dryers")
  @PostMapping("/washer-dryer/arrow-image")
  public Object washerDryerInternetLabel(@RequestBody @Valid WashingMachinesInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            WashingMachinesService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.WASHING_MACHINES_WASHER_DRYER
        ));
  }
}
