package uk.gov.beis.els.categories.washingmachines.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;

public class WashingMachineFormSequenceProvider implements DefaultGroupSequenceProvider<WashingMachinesForm> {
  @Override
  public List<Class<?>> getValidationGroups(WashingMachinesForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (WashingMachinesService.LEGISLATION_CATEGORY_PRE_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreMarch2021Field.class);
      } else if (WashingMachinesService.LEGISLATION_CATEGORY_POST_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostMarch2021Field.class);
      }
    }
    sequence.add(WashingMachinesForm.class);
    return sequence;
  }
}
