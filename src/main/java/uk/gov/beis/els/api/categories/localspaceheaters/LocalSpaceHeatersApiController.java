package uk.gov.beis.els.api.categories.localspaceheaters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.gov.beis.els.categories.localspaceheaters.service.LocalSpaceHeatersService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/local-space-heaters")
@Tag(name = "Local space heaters")
public class LocalSpaceHeatersApiController {

  private final LocalSpaceHeatersService localSpaceHeatersService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public LocalSpaceHeatersApiController(
      LocalSpaceHeatersService localSpaceHeatersService,
      InternetLabelService internetLabelService, DocumentRendererService documentRendererService) {
    this.localSpaceHeatersService = localSpaceHeatersService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Local space heaters: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 105mm x 200mm when printed."
  )
  @PostMapping("/energy-label")
  public Object localSpaceHeaters(@RequestBody @Valid LocalSpaceHeatersForm form) {
    return documentRendererService.processApiResponse(localSpaceHeatersService.generateHtml(form));
  }

  @Operation(summary = "Local space heaters: arrow image")
  @PostMapping("/arrow-image")
  public Object localSpaceHeatersInternetLabel(@RequestBody @Valid LocalSpaceHeatersInternetLabelApiForm form) {
    return documentRendererService.processInternetLabelApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            LocalSpaceHeatersService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.LOCAL_SPACE_HEATERS
        ));
  }
}
