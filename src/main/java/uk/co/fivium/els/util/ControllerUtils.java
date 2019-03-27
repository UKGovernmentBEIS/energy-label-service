package uk.co.fivium.els.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.model.GoogleAnalyticsEventCategory;
import uk.co.fivium.els.model.GoogleAnalyticsEventAction;

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

  public static Map<String, String> combinedLegislationCategorySecondaryRangesToSelectionMap(List<SelectableLegislationCategory> legislationCategories) {
    return legislationCategories.stream()
      .flatMap(r -> r.getSecondaryRatingRange().getApplicableRatings().stream())
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

  public static void sendGoogleAnalyticsEvent(String clientId, GoogleAnalyticsEventCategory eventCategory, GoogleAnalyticsEventAction action, String eventLabel) {
    // We might not get a client ID if the user has JavaScript disabled or blocks the Google Analytics JS.
    // In that case, we generate a random client ID so we can track this event, but it won't be linked to the rest of
    // the user's actions in the service
    if(clientId.isEmpty()) {
      clientId = UUID.randomUUID().toString();
    }

    final String uri = "https://www.google-analytics.com/collect";

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

    // See https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#event for information on the
    // params we're sending
    map.add("v", "1");
    map.add("tid", "UA-136887405-1");
    map.add("cid", clientId);
    map.add("t", "event");
    map.add("ec", eventCategory.getDisplayValue());
    map.add("ea", action.getDisplayValue());
    map.add("el", eventLabel);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
  }

}
