package uk.gov.beis.els.api.categories.dishwashers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/dishwasher")
@Tag(name = "Dishwashers")
public class DishwashersApiController {

  private final DishwashersService dishwashersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public DishwashersApiController(DishwashersService dishwashersService,
                                  InternetLabelService internetLabelService,
                                  DocumentRendererService documentRendererService) {
    this.dishwashersService = dishwashersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for dishwashers",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in dishwasher it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/energy-label")
  public Object dishwashers(@RequestBody @Valid DishwashersForm form) {
    return documentRendererService.processPdfApiResponse(dishwashersService.generateHtml(form));
  }

  @Operation(summary = "Create an internet label for dishwashers")
  @PostMapping("/internet-label")
  public Object dishwashersInternetLabel(@RequestBody @Valid DishwashersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            DishwashersService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.DISHWASHERS
        ));
  }
}
