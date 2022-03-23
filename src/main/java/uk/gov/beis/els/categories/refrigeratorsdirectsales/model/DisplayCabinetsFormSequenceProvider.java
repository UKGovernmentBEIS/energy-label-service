package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class DisplayCabinetsFormSequenceProvider implements DefaultGroupSequenceProvider<DisplayCabinetsForm> {

  @Override
  public List<Class<?>> getValidationGroups(DisplayCabinetsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getFridgeCompartment())) {
        sequence.add(FridgeGroup.class);
      }
      if (BooleanUtils.isTrue(form.getFrozenCompartment())) {
        sequence.add(FreezerGroup.class);
      }
    }

    sequence.add(DisplayCabinetsForm.class);
    return sequence;
  }
}
