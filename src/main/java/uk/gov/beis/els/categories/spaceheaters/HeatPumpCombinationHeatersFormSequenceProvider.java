package uk.gov.beis.els.categories.spaceheaters;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SoundPowerLevelIndoorsGroup;

public class HeatPumpCombinationHeatersFormSequenceProvider implements DefaultGroupSequenceProvider<HeatPumpCombinationHeatersForm> {

  @Override
  public List<Class<?>> getValidationGroups(HeatPumpCombinationHeatersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (!StringUtils.isBlank(form.getSoundPowerLevelIndoors())) {
        sequence.add(SoundPowerLevelIndoorsGroup.class);
      }
    }

    sequence.add(HeatPumpCombinationHeatersForm.class);
    return sequence;
  }

}
