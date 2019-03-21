package uk.co.fivium.els.categories.airconditioners.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class HeatingDuctlessAirConditionersFormSequenceProvider implements DefaultGroupSequenceProvider<HeatingDuctlessAirConditionersForm> {

  @Override
  public List<Class<?>> getValidationGroups(HeatingDuctlessAirConditionersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getWarmerConditions())) {
        sequence.add(WarmerClimateGroup.class);
      }
      if (BooleanUtils.isTrue(form.getColderConditions())) {
        sequence.add(ColderClimateGroup.class);
      }
    }

    sequence.add(HeatingDuctlessAirConditionersForm.class);
    return sequence;
  }
}
