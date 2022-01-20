package uk.gov.beis.els.api.categories.domesticovens;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.domesticovens.model.DomesticOvensForm;
import uk.gov.beis.els.categories.domesticovens.model.GasOvensForm;
import uk.gov.beis.els.categories.domesticovens.service.DomesticOvensService;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/domestic-ovens")
@Tag(name = "Domestic ovens")
public class DomesticOvensApiController {

  private final DomesticOvensService domesticOvensService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  public DomesticOvensApiController(DomesticOvensService domesticOvensService,
                                    InternetLabelService internetLabelService,
                                    DocumentRendererService documentRendererService) {
    this.domesticOvensService = domesticOvensService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
    summary = "Create an energy label for an electric oven",
    description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 85mm x 170mm when printed."
  )
  @PostMapping("/electric-ovens/energy-label")
  public Object electricOvens(@RequestBody @Valid DomesticOvensForm form) {
    return documentRendererService.processPdfApiResponse(domesticOvensService.generateHtml(form));
  }

  @Operation(summary = "Create an internet label for an electric oven")
  @PostMapping("/electric-ovens/internet-label")
  public Object electricOvensInternetLabel(@Valid @RequestBody DomesticOvenInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), DomesticOvensService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.OVENS_ELECTRIC)
    );
  }

  @Operation(
    summary = "Create an energy label for a gas oven",
    description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 85mm x 170mm when printed."
  )
  @PostMapping("/gas-ovens/energy-label")
  public Object gasOvens(@RequestBody @Valid GasOvensForm form) {
    return documentRendererService.processPdfApiResponse(domesticOvensService.generateHtml(form));
  }

  @Operation(summary = "Create an internet label for an gas oven")
  @PostMapping("/gas-ovens/internet-label")
  public Object gasOvensInternetLabel(@Valid @RequestBody DomesticOvenInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(), DomesticOvensService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.OVENS_GAS)
    );
  }

}
