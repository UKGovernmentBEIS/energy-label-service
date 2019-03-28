package uk.co.fivium.els.categories.common;

import org.jsoup.nodes.Document;
import uk.co.fivium.els.model.ProductMetadata;

public class ProcessedEnergyLabelDocument {

  private final Document document;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final String supplierName;
  private final String modelName;

  public ProcessedEnergyLabelDocument(Document document, ProductMetadata productMetadata, String clientAnalyticsToken,
                                      String supplierName, String modelName) {
    this.document = document;
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.supplierName = supplierName;
    this.modelName = modelName;
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

  public String getSupplierName() {
    return supplierName;
  }

  public String getModelName() {
    return modelName;
  }
}
