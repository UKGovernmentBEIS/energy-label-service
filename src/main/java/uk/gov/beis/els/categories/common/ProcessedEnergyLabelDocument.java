package uk.gov.beis.els.categories.common;

import org.jsoup.nodes.Document;
import uk.gov.beis.els.model.EnergyLabelFormat;
import uk.gov.beis.els.model.ProductMetadata;

public class ProcessedEnergyLabelDocument {

  private final Document document;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final String analyticsEventAction;
  private final EnergyLabelFormat labelFormat;

  public ProcessedEnergyLabelDocument(Document document, ProductMetadata productMetadata, String clientAnalyticsToken, String analyticsEventAction, EnergyLabelFormat labelFormat) {
    this.document = document;
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.analyticsEventAction = analyticsEventAction;
    this.labelFormat = labelFormat;
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

  public EnergyLabelFormat getLabelFormat() {
    return labelFormat;
  }
}
