package uk.gov.beis.els.categories.televisions.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.categories.televisions.service.TelevisionsService;

public class TelevisionsFormSequenceProvider implements DefaultGroupSequenceProvider<TelevisionsForm> {
  @Override
  public List<Class<?>> getValidationGroups(TelevisionsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (TelevisionsService.LEGISLATION_CATEGORY_PRE_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreMarch2021Field.class);
      } else if (TelevisionsService.LEGISLATION_CATEGORY_POST_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostMarch2021Field.class);
        if(BooleanUtils.isTrue(form.getIsHdr())) {
          sequence.add(HdrGroup.class);
        }
      }
    }
    sequence.add(TelevisionsForm.class);
    return sequence;
  }
}
