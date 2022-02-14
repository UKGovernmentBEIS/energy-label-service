package uk.gov.beis.els.categories.spaceheaters;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SoundPowerLevelIndoorsGroup;

public class LowTemperatureHeatPumpSpaceHeatersFormSequenceProvider implements DefaultGroupSequenceProvider<LowTemperatureHeatPumpSpaceHeatersForm> {

  @Override
  public List<Class<?>> getValidationGroups(LowTemperatureHeatPumpSpaceHeatersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (!StringUtils.isBlank(form.getSoundPowerLevelIndoors())) {
        sequence.add(SoundPowerLevelIndoorsGroup.class);
      }
    }

    sequence.add(LowTemperatureHeatPumpSpaceHeatersForm.class);
    return sequence;
  }
}
