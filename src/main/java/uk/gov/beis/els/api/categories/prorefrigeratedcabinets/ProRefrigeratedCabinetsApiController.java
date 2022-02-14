package uk.gov.beis.els.api.categories.prorefrigeratedcabinets;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.service.ProRefrigeratedCabinetsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/professional-refrigerated-storage-cabinets")
@Tag(name = "Professional refrigerated storage cabinets")
public class ProRefrigeratedCabinetsApiController {

  private final ProRefrigeratedCabinetsService proRefrigeratedCabinetsService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public ProRefrigeratedCabinetsApiController(
      ProRefrigeratedCabinetsService proRefrigeratedCabinetsService,
      InternetLabelService internetLabelService,
      DocumentRendererService documentRendererService) {
    this.proRefrigeratedCabinetsService = proRefrigeratedCabinetsService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for professional refrigerated storage cabinets",
      description = "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 110mm x 220mm when printed."
  )
  @PostMapping("/energy-label")
  public Object proRefrigeratedCabinets(@RequestBody @Valid ProRefrigeratedCabinetsForm form) {
    return documentRendererService.processPdfApiResponse(
        proRefrigeratedCabinetsService.generateHtml(form, ProRefrigeratedCabinetsService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an arrow image for professional refrigerated storage cabinets")
  @PostMapping("/arrow-image")
  public Object proRefrigeratedCabinetsInternetLabel(
      @RequestBody @Valid ProRefrigeratedCabinetsInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            ProRefrigeratedCabinetsService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.PRO_REFRIGERATED_CABINETS
        ));
  }
}
