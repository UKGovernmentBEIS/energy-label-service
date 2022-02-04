package uk.gov.beis.els.categories.refrigeratingappliances.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class FridgesFreezersFormSequenceProvider implements DefaultGroupSequenceProvider<FridgesFreezersForm> {
  @Override
  public List<Class<?>> getValidationGroups(FridgesFreezersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getNonRatedCompartment())) {
        sequence.add(FridgeGroup.class);
      }
      if (BooleanUtils.isTrue(form.getRatedCompartment())) {
        sequence.add(FreezerGroup.class);
      }
    }

    sequence.add(FridgesFreezersForm.class);
    return sequence;
  }
}
