package uk.gov.beis.els.categories.common;

import org.jsoup.nodes.Document;
import uk.gov.beis.els.model.ProductMetadata;

public class ProcessedEnergyLabelDocument {

  private final Document document;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;

  public ProcessedEnergyLabelDocument(Document document, ProductMetadata productMetadata, String clientAnalyticsToken) {
    this.document = document;
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
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
}
