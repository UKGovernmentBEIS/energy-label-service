package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class VendingMachinesFormSequenceProvider implements DefaultGroupSequenceProvider<VendingMachinesForm> {

  @Override
  public List<Class<?>> getValidationGroups(VendingMachinesForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null && BooleanUtils.isTrue(form.getFrozenCompartment())) {
      sequence.add(FreezerGroup.class);
    }

    sequence.add(VendingMachinesForm.class);
    return sequence;
  }
}
