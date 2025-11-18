package uk.gov.beis.els.categories.tumbledryers.model.rescaled;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class RescaledTumbleDryerFromSequenceProvider implements DefaultGroupSequenceProvider<RescaledTumbleDryersForm> {
  @Override
  public List<Class<?>> getValidationGroups(RescaledTumbleDryersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null && BooleanUtils.isTrue(form.getIsCondensing())) {
      sequence.add(CondensingTumbleDryerGroup.class);
    }

    sequence.add(RescaledTumbleDryersForm.class);
    return sequence;
  }
}
