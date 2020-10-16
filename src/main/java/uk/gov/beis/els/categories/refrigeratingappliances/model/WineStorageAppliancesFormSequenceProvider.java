package uk.gov.beis.els.categories.refrigeratingappliances.model;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;

public class WineStorageAppliancesFormSequenceProvider implements DefaultGroupSequenceProvider<WineStorageAppliancesForm> {

  @Override
  public List<Class<?>> getValidationGroups(WineStorageAppliancesForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (RefrigeratingAppliancesService.LEGISLATION_CATEGORY_PRE_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreMarch2021Field.class);
      } else if (RefrigeratingAppliancesService.LEGISLATION_CATEGORY_POST_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostMarch2021Field.class);
      }
    }

    sequence.add(WineStorageAppliancesForm.class);
    return sequence;
  }
}
