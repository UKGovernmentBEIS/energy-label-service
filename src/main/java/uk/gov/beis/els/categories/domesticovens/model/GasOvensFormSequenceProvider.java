package uk.gov.beis.els.categories.domesticovens.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

// TODO see if the sequence providers can be consolidated
public class GasOvensFormSequenceProvider implements DefaultGroupSequenceProvider<GasOvensForm> {

  @Override
  public List<Class<?>> getValidationGroups(GasOvensForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getIsFanOven())) {
        sequence.add(FanOvenGroup.class);
      }
    }

    sequence.add(GasOvensForm.class);
    return sequence;
  }
}
