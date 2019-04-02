package uk.gov.beis.els.categories.waterheaters.model.validation;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.SolarWaterHeatersForm;

public class SolarWaterHeatersFormSequenceProvider implements DefaultGroupSequenceProvider<SolarWaterHeatersForm> {

  @Override
  public List<Class<?>> getValidationGroups(SolarWaterHeatersForm form) {
    List<Class<?>> sequence = new ArrayList<>();

    if (form != null) {
      if (EnergyConsumptionUnit.KWH.name().equals(form.getConsumptionUnit())) {
        sequence.add(ConsumptionUnitKw.class);
      } else if (EnergyConsumptionUnit.GJ.name().equals(form.getConsumptionUnit())) {
        sequence.add(ConsumptionUnitGj.class);
      } else if (EnergyConsumptionUnit.BOTH.name().equals(form.getConsumptionUnit())) {
        sequence.add(ConsumptionUnitBoth.class);
      }
    }

    sequence.add(SolarWaterHeatersForm.class);
    return sequence;
  }
}
