package uk.gov.beis.els.categories.domesticovens.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class DomesticOvensFormSequenceProvider implements DefaultGroupSequenceProvider<DomesticOvensForm> {

  @Override
  public List<Class<?>> getValidationGroups(DomesticOvensForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getIsFanOven())) {
        sequence.add(FanOvenGroup.class);
      }
    }

    sequence.add(DomesticOvensForm.class);
    return sequence;
  }
}
