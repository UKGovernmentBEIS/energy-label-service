package uk.gov.beis.els.categories.spaceheaters.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class BoilerPackagesCalculatorFormSequenceProvider implements DefaultGroupSequenceProvider<BoilerPackagesCalculatorForm> {

  @Override
  public List<Class<?>> getValidationGroups(BoilerPackagesCalculatorForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    //need to repeat same checks as in SpaceHeaterPackagesCalculatorFormSequenceProvider because the sequence provider gets override by this
    if (form != null && BooleanUtils.isTrue(form.getHasTemperatureControl())) {
      sequence.add(TemperatureControlGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.getHasSupplementaryBoiler())) {
      sequence.add(SupplementaryBoilerGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.getHasStorageTank())) {
      sequence.add(StorageTankGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.getHasSupplementaryHeatPump())) {
      sequence.add(SupplementaryHeatPumpGroup.class);
    }

    sequence.add(BoilerPackagesCalculatorForm.class);
    return sequence;
  }
}
