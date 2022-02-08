package uk.gov.beis.els.api.categories.televisions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.categories.televisions.service.TelevisionsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/televisions")
@Tag(name = "Televisions")
public class TelevisionsApiController {

  private final TelevisionsService televisionsService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public TelevisionsApiController(TelevisionsService televisionsService,
                                  InternetLabelService internetLabelService,
                                  DocumentRendererService documentRendererService) {
    this.televisionsService = televisionsService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for televisions",
      description = "You must display the label so that it’s easy to see and clearly related to the product. Labels must usually be at least 96mm x 192mm when printed. If the diagonal size of the visible screen area is less than 127cm (50 inches) you can scale down the the label, but it must still be at least 57.6mm x 115.2mm. If you scale down the label you must test that the QR code can still be read when printed, for example by using a smartphone camera."
  )
  @PostMapping("/energy-label")
  public Object televisions(@RequestBody @Valid TelevisionsForm form) {
    return documentRendererService.processPdfApiResponse(televisionsService.generateHtml(form));
  }

  @Operation(summary = "Create an internet label for televisions")
  @PostMapping("/internet-label")
  public Object televisionsInternetLabel(@RequestBody @Valid TelevisionsInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            DishwashersService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.TV
        ));
  }
}
