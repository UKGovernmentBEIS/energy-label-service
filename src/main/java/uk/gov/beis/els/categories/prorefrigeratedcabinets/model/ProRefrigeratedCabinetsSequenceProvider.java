package uk.gov.beis.els.categories.prorefrigeratedcabinets.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class ProRefrigeratedCabinetsSequenceProvider implements DefaultGroupSequenceProvider<ProRefrigeratedCabinetsForm> {

  @Override
  public List<Class<?>> getValidationGroups(ProRefrigeratedCabinetsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getChilledCompartment())) {
        sequence.add(FridgeGroup.class);
      }
      if (BooleanUtils.isTrue(form.getFrozenCompartment())) {
        sequence.add(FreezerGroup.class);
      }
    }

    sequence.add(ProRefrigeratedCabinetsForm.class);
    return sequence;
  }
}
