package uk.co.fivium.els.categories.refrigeratingappliances.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.co.fivium.els.categories.airconditioners.model.ColderClimateGroup;
import uk.co.fivium.els.categories.airconditioners.model.ReversibleAirConditionersForm;
import uk.co.fivium.els.categories.airconditioners.model.WarmerClimateGroup;

import java.util.ArrayList;
import java.util.List;

public class RefrigeratingAppliancesFormSequenceProvider implements DefaultGroupSequenceProvider<FridgesFreezersForm> {

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
