package uk.gov.beis.els.api.categories.refrigeratingappliances;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.gov.beis.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/household-refrigerating-appliances")
@Tag(name = "Household refrigerating appliances")
public class RefrigeratingAppliancesApiController {

  private final RefrigeratingAppliancesService refrigeratingAppliancesService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RefrigeratingAppliancesApiController(
      RefrigeratingAppliancesService refrigeratingAppliancesService,
      InternetLabelService internetLabelService, DocumentRendererService documentRendererService) {
    this.refrigeratingAppliancesService = refrigeratingAppliancesService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Household fridges and freezers: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in appliance it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/household-fridges-and-freezers/energy-label")
  public Object fridgesFreezers(@RequestBody @Valid FridgesFreezersForm form) {
    return documentRendererService.processPdfApiResponse(refrigeratingAppliancesService.generateHtml(form));
  }

  @Operation(summary = "Household fridges and freezers: arrow image")
  @PostMapping("/household-fridges-and-freezers/arrow-image")
  public Object fridgesFreezersInternetLabel(@RequestBody @Valid RefrigeratingAppliancesInternetLapelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            RefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.HRA_FRIDGE_FREEZER
        ));
  }

  @Operation(
      summary = "Wine storage appliances: energy label",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in appliance it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/wine-storage-appliances/energy-label")
  public Object wineStorageAppliances(@RequestBody @Valid WineStorageAppliancesForm form) {
    return documentRendererService.processPdfApiResponse(refrigeratingAppliancesService.generateHtml(form));
  }

  @Operation(summary = "Wine storage appliances: arrow image")
  @PostMapping("/wine-storage-appliances/arrow-image")
  public Object wineStorageAppliancesInternetLabel(@RequestBody @Valid RefrigeratingAppliancesInternetLapelApiForm form) {
    return documentRendererService.processImageApiResponse(
        internetLabelService.generateInternetLabel(form,
            form.getEfficiencyRating(),
            RefrigeratingAppliancesService.LEGISLATION_CATEGORY_CURRENT,
            ProductMetadata.HRA_WINE_STORAGE
        ));
  }
}
