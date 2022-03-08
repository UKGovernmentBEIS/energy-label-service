package uk.gov.beis.els.categories.spaceheaters.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class SpaceHeaterPackagesCalculatorFormSequenceProvider implements DefaultGroupSequenceProvider<SpaceHeaterPackagesCalculatorForm> {

  @Override
  public List<Class<?>> getValidationGroups(SpaceHeaterPackagesCalculatorForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null && BooleanUtils.isTrue(form.isHasTemperatureControl())) {
      sequence.add(TemperatureControlGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.isHasSupplementaryBoiler())) {
      sequence.add(SupplementaryBoilerGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.isHasSolarCollector())) {
      sequence.add(SolarCollectorGroup.class);
    }

    if (form != null && BooleanUtils.isTrue(form.isHasStorageTank())) {
      sequence.add(StorageTankGroup.class);
    }

    sequence.add(SpaceHeaterPackagesCalculatorForm.class);
    return sequence;
  }
}
