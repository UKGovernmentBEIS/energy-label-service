package uk.gov.beis.els.categories.rangehoods;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.rangehoods.model.LightingSystemGroup;
import uk.gov.beis.els.categories.rangehoods.model.RangeHoodsForm;

public class RangeHoodsFormSequenceProvider implements DefaultGroupSequenceProvider<RangeHoodsForm> {

  @Override
  public List<Class<?>> getValidationGroups(RangeHoodsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (BooleanUtils.isTrue(form.getLightingSystem())) {
        sequence.add(LightingSystemGroup.class);
      }
    }

    sequence.add(RangeHoodsForm.class);
    return sequence;
  }
}
