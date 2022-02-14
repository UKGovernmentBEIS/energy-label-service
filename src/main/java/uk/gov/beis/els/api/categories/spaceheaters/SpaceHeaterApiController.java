package uk.gov.beis.els.api.categories.spaceheaters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/space-heaters")
@Tag(name = "Space heaters")
public class SpaceHeaterApiController {

  private final SpaceHeatersService spaceHeatersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  public SpaceHeaterApiController(SpaceHeatersService spaceHeatersService,
                                  InternetLabelService internetLabelService,
                                  DocumentRendererService documentRendererService) {
    this.spaceHeatersService = spaceHeatersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Boiler space heater energy label",
      description = "You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/boiler-space-heaters/energy-label")
  public Object boilerSpaceHeater(@RequestBody @Valid BoilerSpaceHeatersForm form) {
    return documentRendererService.processPdfApiResponse(
        spaceHeatersService.generateHtml(form, SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Boiler space heater arrow image")
  @PostMapping("/boiler-space-heaters/arrow-image")
  public Object boilerSpaceHeater(@RequestBody @Valid SpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form, form.getEfficiencyRating(),
            SpaceHeatersService.LEGISLATION_CATEGORY_CURRENT, ProductMetadata.SPACE_HEATER_BOILER));
  }
}
