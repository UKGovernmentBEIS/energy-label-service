package uk.gov.beis.els.api.model;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;

@SuppressWarnings("rawtypes")
public class OperationWithSchema {

  private final Operation operation;

  private final String tag;

  private final Schema schema;

  private final String example;

  public OperationWithSchema(Operation operation, String tag, Schema schema, String example) {
    this.operation = operation;
    this.tag = tag;
    this.schema = schema;
    this.example = example;
  }

  public Operation getOperation() {
    return operation;
  }

  public String getTag() {
    return tag;
  }

  public Schema getSchema() {
    return schema;
  }

  public String getExample() {
    return example;
  }
}
