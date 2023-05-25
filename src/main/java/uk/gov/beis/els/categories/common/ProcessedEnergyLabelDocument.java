package uk.gov.beis.els.categories.common;

import org.jsoup.nodes.Document;
import uk.gov.beis.els.model.EnergyLabelFormat;
import uk.gov.beis.els.model.GoogleAnalyticsEventParams;
import uk.gov.beis.els.model.ProductMetadata;

public class ProcessedEnergyLabelDocument {

  private final Document document;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final GoogleAnalyticsEventParams analyticsEventParams;
  private final EnergyLabelFormat labelFormat;

  public ProcessedEnergyLabelDocument(Document document, ProductMetadata productMetadata, String clientAnalyticsToken, GoogleAnalyticsEventParams analyticsEventParams, EnergyLabelFormat labelFormat) {
    this.document = document;
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.analyticsEventParams = analyticsEventParams;
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

  public GoogleAnalyticsEventParams getAnalyticsEventParams() {
    return analyticsEventParams;
  }

  public EnergyLabelFormat getLabelFormat() {
    return labelFormat;
  }
}
