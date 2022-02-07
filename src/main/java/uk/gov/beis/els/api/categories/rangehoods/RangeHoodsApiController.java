package uk.gov.beis.els.api.categories.rangehoods;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.rangehoods.model.RangeHoodsForm;
import uk.gov.beis.els.categories.rangehoods.service.RangeHoodsService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}")
@Tag(name = "Range hoods")
public class RangeHoodsApiController {

  private final RangeHoodsService rangeHoodsService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RangeHoodsApiController(RangeHoodsService rangeHoodsService,
                                 InternetLabelService internetLabelService,
                                 DocumentRendererService documentRendererService) {
    this.rangeHoodsService = rangeHoodsService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create energy label for range hood",
      description = "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 60mm x 120mm when printed."
  )
  @PostMapping("/range-hoods/energy-label")
  public Object rangeHoods(@RequestBody @Valid RangeHoodsForm form) {
    return documentRendererService.processPdfApiResponse(
        rangeHoodsService.generateHtml(form, RangeHoodsService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an internet label for a range hood")
  @PostMapping("/range-hoods/internet-label")
  public Object rangeHoodsInternetLabel(@RequestBody @Valid RangeHoodsInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            RangeHoodsService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.RANGE_HOODS));
  }
}
