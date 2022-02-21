package uk.gov.beis.els.api.categories.waterheaters;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitBoth;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitGj;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitKw;

public class HeatPumpWaterHeatersApiFormSequenceProvider implements DefaultGroupSequenceProvider<HeatPumpWaterHeatersApiForm> {

  @Override
  public List<Class<?>> getValidationGroups(HeatPumpWaterHeatersApiForm form) {
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

    sequence.add(HeatPumpWaterHeatersApiForm.class);
    return sequence;
  }
}
