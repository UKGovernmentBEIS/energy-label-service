package uk.gov.beis.els.categories.spaceheaters.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class BoilerPackagesCalculatorFormSequenceProvider implements DefaultGroupSequenceProvider<BoilerPackagesCalculatorForm> {

  @Override
  public List<Class<?>> getValidationGroups(BoilerPackagesCalculatorForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null && BooleanUtils.isTrue(form.isHasSupplementaryHeatPump())) {
      sequence.add(SupplementaryHeatPumpGroup.class);
    }

    sequence.add(BoilerPackagesCalculatorForm.class);
    return sequence;
  }
}
