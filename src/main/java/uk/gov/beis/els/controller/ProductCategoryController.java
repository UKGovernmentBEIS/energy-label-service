package uk.gov.beis.els.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.beis.els.model.ProductCategory;
import uk.gov.beis.els.service.BreadcrumbService;

@Controller
@RequestMapping("/categories")
public class ProductCategoryController extends CategoryController {

  @Autowired
  public ProductCategoryController(BreadcrumbService breadcrumbService) {
    super("Product category", breadcrumbService, ProductCategory.GET, ProductCategoryController.class);
  }

}
