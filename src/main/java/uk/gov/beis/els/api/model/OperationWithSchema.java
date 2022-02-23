package uk.gov.beis.els.api.model;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;

@SuppressWarnings("rawtypes")
public class OperationWithSchema {

  private Operation operation;

  private Schema schema;

  public OperationWithSchema() {
  }

  public OperationWithSchema(Operation operation, Schema schema) {
    this.operation = operation;
    this.schema = schema;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  public Schema getSchema() {
    return schema;
  }

  public void setSchema(Schema schema) {
    this.schema = schema;
  }
}
