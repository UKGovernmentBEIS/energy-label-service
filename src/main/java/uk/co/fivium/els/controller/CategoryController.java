package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

public class CategoryController {

  private final String stageText;
  private final BreadcrumbService breadcrumbService;
  private final Category category;
  private Class<? extends CategoryController> controllerClass;

  public CategoryController(String stageText, BreadcrumbService breadcrumbService,
                            Category category, Class<? extends CategoryController> controllerClass) {
    this.stageText = stageText;
    this.breadcrumbService = breadcrumbService;
    this.category = category;
    this.controllerClass = controllerClass;
  }

  @GetMapping("")
  public ModelAndView renderCategories(@ModelAttribute("form") StandardCategoryForm form) {
    return getCategoryModelAndView(Collections.emptyList());
  }

  @PostMapping("")
  @ResponseBody
  public ModelAndView handleCategoriesSubmit(@Valid @ModelAttribute("form") StandardCategoryForm form, BindingResult bindingResult) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required", category.getNoSelectionErrorMessage());
      return getCategoryModelAndView(bindingResult.getFieldErrors());
    } else {
      CategoryItem categoryItem = category.getCategoryItem(form.getCategory());
      return new ModelAndView("redirect:" + categoryItem.getNextStateUrl());
    }
  }

  public void addCommonProductGuidance(ModelAndView modelAndView) {
    modelAndView.addObject("commonProductGuidance", category.getCommonProductGuidanceText());
  }

  private ModelAndView getCategoryModelAndView(List<FieldError> errors) {

    ModelAndView modelAndView = new ModelAndView("standardCategorySelectionPage");
    modelAndView.addObject("title", category.getCategoryQuestionText());
    modelAndView.addObject("categories",
        category.getCategoryItems().stream().collect(StreamUtils.toLinkedHashMap(CategoryItem::getId, CategoryItem::getName))
    );
    ControllerUtils.addErrorSummary(modelAndView, errors);
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(controllerClass).handleCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
    modelAndView.addObject("categoryGuidanceText", category.getCategoryPageGuidanceText());
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, stageText);
    return modelAndView;
  }
}
