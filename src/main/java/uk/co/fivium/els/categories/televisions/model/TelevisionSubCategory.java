package uk.co.fivium.els.categories.televisions.model;

import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.televisions.controller.TelevisionsController;
import uk.co.fivium.els.mvc.ReverseRouter;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public enum TelevisionSubCategory implements Category {

  TELEVISIONS_FROM_JAN2017("TV Classes A++ to E applicable from 1 January 2017",
    ReverseRouter.route(on(TelevisionsController.class).renderTelevisionsFrom2017(null))),
  TELEVISIONS_FROM_JAN2020("TV Classes A+++ to D applicable from 1 January 2020",
    ReverseRouter.route(on(TelevisionsController.class).renderTelevisionsFrom2020(null)));

  public static String getCategoryQuestionText() {
    return "What type of television label do you need?";
  }

  public static String getNoSelectionErrorMessage() {
    return "Select a type of television label";
  }

  private final String displayName;
  private final String nextStateUrl;

  TelevisionSubCategory(String displayName, String nextStateUrl) {
    this.displayName = displayName;
    this.nextStateUrl = nextStateUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNextStateUrl() {
    return nextStateUrl;
  }

  public String getName() {
    return this.name();
  }

}
