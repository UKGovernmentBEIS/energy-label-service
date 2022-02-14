package uk.gov.beis.els.categories.spaceheaters;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SoundPowerLevelIndoorsGroup;

public class BoilerCombinationHeatersFormSequenceProvider implements DefaultGroupSequenceProvider<BoilerCombinationHeatersForm> {

  @Override
  public List<Class<?>> getValidationGroups(BoilerCombinationHeatersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (!StringUtils.isBlank(form.getSoundPowerLevelIndoors())) {
        sequence.add(SoundPowerLevelIndoorsGroup.class);
      }
    }

    sequence.add(BoilerCombinationHeatersForm.class);
    return sequence;
  }
}
