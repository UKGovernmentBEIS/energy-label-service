package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.model.ProductCategory;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;

@Controller
public class ProductCategoryController {

  private final String BREADCRUMB_STAGE_TEXT = "Product category";

  private final BreadcrumbService breadcrumbService;

  @Autowired
  public ProductCategoryController(BreadcrumbService breadcrumbService) {
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView redirectToCategories() {
    return ReverseRouter.redirect(on(ProductCategoryController.class).renderCategories(null));
  }


  @GetMapping("/categories")
  public ModelAndView renderCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getProductCategories(Collections.emptyList());
  }

  @PostMapping("/categories")
  public ModelAndView handleCategoriesSubmit(@Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required", ProductCategory.getNoSelectionErrorMessage());
      return getProductCategories(bindingResult.getFieldErrors());
    } else {
      ProductCategory category = ProductCategory.valueOf(form.getCategory());
      return new ModelAndView("redirect:" + category.getNextStateUrl());
    }
  }

  private ModelAndView getProductCategories(List<FieldError> errors) {
    return ControllerUtils.getCategorySelectionModelAndView(ProductCategory.getCategoryQuestionText(),
        ProductCategory.values(),
        errors,
        ReverseRouter.route(on(ProductCategoryController.class).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())),
        BREADCRUMB_STAGE_TEXT,
        breadcrumbService
    );
  }

  // TODO remove route and template once all categories are completed
  @GetMapping("/not-yet-implemented")
  public ModelAndView renderNotYetImplemented() {
    ModelAndView modelAndView = new ModelAndView("notYetImplemented");
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Not available");
    return modelAndView;
  }

}
