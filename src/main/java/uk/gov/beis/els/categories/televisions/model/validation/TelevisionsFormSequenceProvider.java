package uk.gov.beis.els.categories.televisions.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;

public class TelevisionsFormSequenceProvider implements DefaultGroupSequenceProvider<TelevisionsForm> {
  @Override
  public List<Class<?>> getValidationGroups(TelevisionsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if(BooleanUtils.isTrue(form.getIsHdr())) {
        sequence.add(HdrGroup.class);
      }
    }
    sequence.add(TelevisionsForm.class);
    return sequence;
  }
}
