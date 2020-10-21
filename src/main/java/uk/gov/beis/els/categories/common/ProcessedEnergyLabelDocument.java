package uk.gov.beis.els.categories.common;

import org.jsoup.nodes.Document;
import uk.gov.beis.els.model.ProductMetadata;

public class ProcessedEnergyLabelDocument {

  private final Document document;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final String analyticsEventAction;

  public ProcessedEnergyLabelDocument(Document document, ProductMetadata productMetadata, String clientAnalyticsToken, String analyticsEventAction) {
    this.document = document;
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.analyticsEventAction = analyticsEventAction;
  }

  public Document getDocument() {
    return document;
  }

  public ProductMetadata getProductMetadata() {
    return productMetadata;
  }

  public String getClientAnalyticsToken() {
    return clientAnalyticsToken;
  }

  public String getAnalyticsEventAction() {
    return analyticsEventAction;
  }
}
