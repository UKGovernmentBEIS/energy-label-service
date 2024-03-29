package uk.gov.beis.els.categories.common;

import org.jsoup.nodes.Document;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.model.GoogleAnalyticsEventParams;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;

public class ProcessedInternetLabelDocument {

  private final Document document;
  private final RatingClass ratingClass;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final InternetLabelFormat internetLabelFormat;
  private final GoogleAnalyticsEventParams analyticsEventParams;

  public ProcessedInternetLabelDocument(Document document, String ratingClass,
                                        ProductMetadata productMetadata, String clientAnalyticsToken,
                                        String internetLabelFormat, GoogleAnalyticsEventParams analyticsEventParams) {
    this.document = document;
    this.ratingClass = RatingClass.getEnum(ratingClass);
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.internetLabelFormat = InternetLabelFormat.valueOf(internetLabelFormat);
    this.analyticsEventParams = analyticsEventParams;
  }

  public Document getDocument() {
    return document;
  }

  public RatingClass getRatingClass() {
    return ratingClass;
  }

  public ProductMetadata getProductMetadata() {
    return productMetadata;
  }

  public String getClientAnalyticsToken() {
    return clientAnalyticsToken;
  }

  public InternetLabelFormat getInternetLabelFormat() {
    return internetLabelFormat;
  }

  public GoogleAnalyticsEventParams getAnalyticsEventParams() {
    return analyticsEventParams;
  }
}
