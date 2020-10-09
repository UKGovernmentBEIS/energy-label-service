package uk.gov.beis.els.categories.dishwashers.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;

public class DishwashersFormSequenceProvider implements DefaultGroupSequenceProvider<DishwashersForm> {
  @Override
  public List<Class<?>> getValidationGroups(DishwashersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (DishwashersService.LEGISLATION_CATEGORY_PRE_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreMarch2021Field.class);
      } else if (DishwashersService.LEGISLATION_CATEGORY_POST_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostMarch2021Field.class);
      }
    }
    sequence.add(DishwashersForm.class);
    return sequence;
  }
}
