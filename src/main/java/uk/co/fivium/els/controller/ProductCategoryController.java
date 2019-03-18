package uk.co.fivium.els.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.fivium.els.model.ProductCategory;
import uk.co.fivium.els.service.BreadcrumbService;

@Controller
@RequestMapping("/categories")
public class ProductCategoryController extends CategoryController {

  @Autowired
  public ProductCategoryController(BreadcrumbService breadcrumbService) {
    super("Product category", breadcrumbService, ProductCategory.GET, ProductCategoryController.class);
  }

}
