package uk.co.fivium.els.categories.prorefrigeratedcabinets.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.co.fivium.els.categories.refrigeratingappliances.model.FreezerGroup;
import uk.co.fivium.els.categories.refrigeratingappliances.model.FridgeGroup;

import java.util.ArrayList;
import java.util.List;

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
