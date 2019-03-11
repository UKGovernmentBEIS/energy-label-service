package uk.co.fivium.elp.categories.airconditioners.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class ReversibleAirConditionersFormSequenceProvider implements DefaultGroupSequenceProvider<ReversibleAirConditionersForm> {

  @Override
  public List<Class<?>> getValidationGroups(ReversibleAirConditionersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getWamerScopAvailable())) {
        sequence.add(WarmerClimateGroup.class);
      }
      if (BooleanUtils.isTrue(form.getColderScopAvailable())) {
        sequence.add(ColderClimateGroup.class);
      }
    }

    sequence.add(ReversibleAirConditionersForm.class);
    return sequence;
  }
}
