package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.ProductCategory;
import uk.co.fivium.els.model.ProductCategoryForm;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.util.StreamUtils;

@Controller
public class ProductCategoryController {


  @GetMapping("/")
  public ModelAndView redirectToCategories() {
    return ReverseRouter.redirect(on(ProductCategoryController.class).renderCategories(null));
  }


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
      return new ModelAndView("redirect:" + subCategory.getNextStateUrl());
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
