package uk.co.fivium.elp.controller;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.elp.model.ProductCategory;
import uk.co.fivium.elp.model.ProductCategoryForm;
import uk.co.fivium.elp.util.StreamUtils;

@Controller
public class ProductCategoryController {


  @GetMapping("/categories")
  public ModelAndView renderCategories(@ModelAttribute("form") ProductCategoryForm form) {
    return getModelAndView();
  }

  @PostMapping("/categories")
  public ModelAndView renderCategories(@ModelAttribute("form") ProductCategoryForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getModelAndView();
    } else {
      ProductCategory subCategory = ProductCategory.valueOf(form.getCategory());
      return new ModelAndView("redirect:" + subCategory.getNextSateUrl());
    }

  }

  private ModelAndView getModelAndView() {
    ModelAndView modelAndView = new ModelAndView("productCategory");
    modelAndView.addObject("categories",
        Arrays.stream(ProductCategory.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, ProductCategory::getDisplayName))
    );

    return modelAndView;
  }

}
