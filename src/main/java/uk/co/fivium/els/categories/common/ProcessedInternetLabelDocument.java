package uk.co.fivium.els.categories.common;

import org.jsoup.nodes.Document;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.co.fivium.els.model.ProductMetadata;
import uk.co.fivium.els.model.RatingClass;

public class ProcessedInternetLabelDocument {

  private final Document document;
  private final RatingClass ratingClass;
  private final ProductMetadata productMetadata;
  private final String clientAnalyticsToken;
  private final InternetLabelFormat internetLabelFormat;

  public ProcessedInternetLabelDocument(Document document, String ratingClass,
                                        ProductMetadata productMetadata, String clientAnalyticsToken,
                                        String internetLabelFormat) {
    this.document = document;
    this.ratingClass = RatingClass.valueOf(ratingClass);
    this.productMetadata = productMetadata;
    this.clientAnalyticsToken = clientAnalyticsToken;
    this.internetLabelFormat = InternetLabelFormat.valueOf(internetLabelFormat);
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
}
