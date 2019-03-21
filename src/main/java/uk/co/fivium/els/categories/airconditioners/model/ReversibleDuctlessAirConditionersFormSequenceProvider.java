package uk.co.fivium.els.categories.airconditioners.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ReversibleDuctlessAirConditionersFormSequenceProvider implements DefaultGroupSequenceProvider<ReversibleDuctlessAirConditionersForm> {

  @Override
  public List<Class<?>> getValidationGroups(ReversibleDuctlessAirConditionersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getWarmerConditions())) {
        sequence.add(WarmerClimateGroup.class);
      }
      if (BooleanUtils.isTrue(form.getColderConditions())) {
        sequence.add(ColderClimateGroup.class);
      }
    }

    sequence.add(ReversibleDuctlessAirConditionersForm.class);
    return sequence;
  }
}
