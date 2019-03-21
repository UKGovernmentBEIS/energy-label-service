package uk.co.fivium.els.categories.localspaceheaters.model;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class LocalSpaceHeatersFormSequenceProvider implements DefaultGroupSequenceProvider<LocalSpaceHeatersForm> {

  @Override
  public List<Class<?>> getValidationGroups(LocalSpaceHeatersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getFluidTransfer())) {
        sequence.add(HeatTransferGroup.class);
      }
    }

    sequence.add(LocalSpaceHeatersForm.class);
    return sequence;
  }
}
