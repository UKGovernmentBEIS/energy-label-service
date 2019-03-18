package uk.co.fivium.els.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;

public class ControllerUtils {

  public static ResponseEntity serveResource(Resource resource, String filename) {
    try {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .contentLength(resource.contentLength())
          .header(
              HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", filename))
          .body(resource);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Error serving PDF file '%s'", filename), e);
    }
  }

  public static void addErrorSummary(ModelAndView modelAndView, List<FieldError> errorList) {
    modelAndView.addObject("errorList", errorList.stream()
        .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
    );
  }

  public static Map<String, String> ratingRangeToSelectionMap(RatingClassRange ratingClassRange) {
    return ratingClassRange.getApplicableRatings().stream()
        .collect(StreamUtils.toLinkedHashMap(Enum::name, RatingClass::getDisplayValue));
  }

  public static Map<String, String> combinedLegislationCategoryRangesToSelectionMap(List<SelectableLegislationCategory> legislationCategories) {
    return legislationCategories.stream()
        .flatMap(r -> r.getPrimaryRatingRange().getApplicableRatings().stream())
        .sorted(Comparator.comparing(Enum::ordinal))
        .collect(StreamUtils.toLinkedMergingHashMap(Enum::name, RatingClass::getDisplayValue));
  }

  public static Map<String, String> legislationYearSelection(List<SelectableLegislationCategory> legislationCategories) {
    return legislationCategories.stream()
        .collect(StreamUtils.toLinkedHashMap(SelectableLegislationCategory::getId, SelectableLegislationCategory::getDisplayName));
  }

  public static void validateRatingClassIfPopulated(String legislationId, String efficiencyRating, List<SelectableLegislationCategory> legislationCategory, BindingResult bindingResult) {
    if (!StringUtils.isBlank(legislationId) && !StringUtils.isBlank(efficiencyRating)) {
      SelectableLegislationCategory category = SelectableLegislationCategory.getById(legislationId, legislationCategory);
      if (!LegislationCategory.isPrimaryRatingClassValid(efficiencyRating, category)) {
        bindingResult.rejectValue("efficiencyRating", "efficiencyRating.invalid", "This rating is not valid for the period your product is on the market");
      }
    }
  }


}
