package uk.gov.beis.els.categories.refrigeratingappliances.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;

public class FridgesFreezersFormSequenceProvider implements DefaultGroupSequenceProvider<FridgesFreezersForm> {

  @Override
  public List<Class<?>> getValidationGroups(FridgesFreezersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (RefrigeratingAppliancesService.LEGISLATION_CATEGORY_PRE_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PreMarch2021Field.class);

        if (BooleanUtils.isTrue(form.getNonRatedCompartmentPreMarch2021())) {
          sequence.add(FridgeGroupPreMarch2021.class);
        }
        if (BooleanUtils.isTrue(form.getRatedCompartmentPreMarch2021())) {
          sequence.add(FreezerGroupPreMarch2021.class);
        }

      } else if (RefrigeratingAppliancesService.LEGISLATION_CATEGORY_POST_MARCH_2021.getId().equals(form.getApplicableLegislation())) {
        sequence.add(PostMarch2021Field.class);

        if (BooleanUtils.isTrue(form.getNonRatedCompartmentPostMarch2021())) {
          sequence.add(FridgeGroupPostMarch2021.class);
        }
        if (BooleanUtils.isTrue(form.getRatedCompartmentPostMarch2021())) {
          sequence.add(FreezerGroupPostMarch2021.class);
        }

      }
    }

    sequence.add(FridgesFreezersForm.class);
    return sequence;
  }
}
