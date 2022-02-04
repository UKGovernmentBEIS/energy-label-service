package uk.gov.beis.els.api.categories.refrigeratordirectsales;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.beis.els.categories.internetlabelling.service.InternetLabelService;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.VendingMachinesForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.service.DocumentRendererService;

@RestController
@RequestMapping("${api.v1.base_path}/refrigerators-direct-sales")
@Tag(name = "Refrigerators with a direct sales function")
public class RefrigeratorsDirectSalesApiController {

  private final RefrigeratorsDirectSalesService refrigeratorsDirectSalesService;
  private final InternetLabelService internetLabelService;
  private final DocumentRendererService documentRendererService;

  @Autowired
  public RefrigeratorsDirectSalesApiController(
      RefrigeratorsDirectSalesService refrigeratorsDirectSalesService,
      InternetLabelService internetLabelService, DocumentRendererService documentRendererService) {
    this.refrigeratorsDirectSalesService = refrigeratorsDirectSalesService;
    this.internetLabelService = internetLabelService;
    this.documentRendererService = documentRendererService;
  }

  @Operation(
      summary = "Create an energy label for refrigerated vending machines",
      description = "You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in appliance it doesn't have to be attached to the product, but it must still be easy to see. It must be at least 96mm x 192mm when printed."
  )
  @PostMapping("/vending-machines/energy-label")
  public Object vendingMachines(@RequestBody @Valid VendingMachinesForm form) {
    return documentRendererService.processPdfApiResponse(refrigeratorsDirectSalesService.generateHtml(form,
        RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT));
  }

  @Operation(summary = "Create an internet label for refrigerated vending machines")
  @PostMapping("/vending-machines/internet-label")
  public Object vendingMachinesInternetLabel(@RequestBody @Valid VendingMachinesInternetLabelApiForm form) {
    return documentRendererService.processImageApiResponse(internetLabelService.generateInternetLabel(form,
        form.getEfficiencyRating(), RefrigeratorsDirectSalesService.LEGISLATION_CATEGORY_CURRENT,
        ProductMetadata.VENDING_MACHINES));
  }
}
