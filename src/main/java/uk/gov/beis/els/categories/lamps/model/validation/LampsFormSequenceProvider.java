package uk.gov.beis.els.categories.lamps.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.lamps.model.LampsForm;

public class LampsFormSequenceProvider implements DefaultGroupSequenceProvider<LampsForm> {
  @Override
  public List<Class<?>> getValidationGroups(LampsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    sequence.add(LampsForm.class);
    return sequence;
  }
}
