package uk.co.fivium.els.categories.common;

public class CategoryItem {

  private final String id;
  private final String name;
  private final String nextStateUrl;

  public CategoryItem(String id, String name, String nextStateUrl) {
    this.id = id;
    this.name = name;
    this.nextStateUrl = nextStateUrl;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNextStateUrl() {
    return nextStateUrl;
  }
}
