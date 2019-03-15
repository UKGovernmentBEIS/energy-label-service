package uk.co.fivium.els.util;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.common.StandardCategoryForm;
import uk.co.fivium.els.categories.common.CategoryItem;
import uk.co.fivium.els.service.BreadcrumbService;

public class ControllerUtils {

  public static ResponseEntity serveResource(Resource resource, String filename) throws IOException {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(resource.contentLength())
        .header(
            HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", filename))
        .body(resource);
  }

  public static void addErrorSummary(ModelAndView modelAndView, List<FieldError> errorList) {
    modelAndView.addObject("errorList", errorList.stream()
        .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
    );
  }

//  public static ModelAndView getCategorySelectionModelAndView(String questionTitle, CategoryOld[] categories, List<FieldError> errors, String submitUrl, String breadcrumbTitle, BreadcrumbService breadcrumbService) {
//    ModelAndView modelAndView = new ModelAndView("standardCategorySelectionPage");
//    modelAndView.addObject("title", questionTitle);
//    modelAndView.addObject("categories",
//        Arrays.stream(categories).collect(StreamUtils.toLinkedHashMap(CategoryOld::getName, CategoryOld::getDisplayName))
//    );
//    ControllerUtils.addErrorSummary(modelAndView, errors);
//    modelAndView.addObject("submitUrl", submitUrl);
//    breadcrumbService.addLastBreadcrumbToModel(modelAndView, breadcrumbTitle);
//    return modelAndView;
//  }

  public static ModelAndView handleSubCategorySubmit(Category category, StandardCategoryForm form, BindingResult bindingResult, Function<List<FieldError>, ModelAndView> viewFunction) {
    if (StringUtils.isBlank(form.getCategory())) {
      ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "category", "category.required", category.getNoSelectionErrorMessage());
      return viewFunction.apply(bindingResult.getFieldErrors());
    } else {
//      LampSubCategory subCategory = LampSubCategory.valueOf(form.getCategory());
      CategoryItem categoryItem = category.getCategoryItem(form.getCategory());
      return new ModelAndView("redirect:" + categoryItem.getNextStateUrl());
    }
  }

  public static ModelAndView getCategorySelectionModelAndView(Category category, List<FieldError> errors, String submitUrl, String breadcrumbTitle, BreadcrumbService breadcrumbService) {
    ModelAndView modelAndView = new ModelAndView("standardCategorySelectionPage");
    modelAndView.addObject("title", category.getCategoryQuestionText());
    modelAndView.addObject("categories",
        category.getCategoryItems().stream().collect(StreamUtils.toLinkedHashMap(CategoryItem::getId, CategoryItem::getName))
    );
    ControllerUtils.addErrorSummary(modelAndView, errors);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, breadcrumbTitle);
    return modelAndView;
  }

}
