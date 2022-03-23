package uk.gov.beis.els.api.openapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.IOException;

/**
 * Jackson pretty printer with a more standardised formatting of the field and value separator:
 * '"field": value' rather than '"field" : value'
 */
public class JsonPrettyPrinter extends DefaultPrettyPrinter {
  @Override
  public void writeObjectFieldValueSeparator(JsonGenerator jg) throws IOException {
    jg.writeRaw(": ");
  }

  @Override
  public DefaultPrettyPrinter createInstance() {
    return new JsonPrettyPrinter();
  }
}
