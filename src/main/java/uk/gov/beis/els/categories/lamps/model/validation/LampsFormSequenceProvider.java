package uk.gov.beis.els.categories.lamps.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.lamps.model.LampsForm;
import uk.gov.beis.els.categories.common.PostSeptember2021Field;
import uk.gov.beis.els.categories.common.PreSeptember2021Field;
import uk.gov.beis.els.categories.lamps.service.LampsService;

public class LampsFormSequenceProvider implements DefaultGroupSequenceProvider<LampsForm> {
  @Override
  public List<Class<?>> getValidationGroups(LampsForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (LampsService.LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreSeptember2021Field.class);
      } else if (LampsService.LEGISLATION_CATEGORY_POST_SEPTEMBER_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostSeptember2021Field.class);
      }
    }
    sequence.add(LampsForm.class);
    return sequence;
  }
}
