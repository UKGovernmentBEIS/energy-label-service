package uk.gov.beis.els.categories.spaceheaters.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.PreferentialHeaterTypes;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.TankLabelClass;
import uk.gov.beis.els.categories.spaceheaters.model.TemperatureControlClass;
import uk.gov.beis.els.model.RatingClass;

@Service
public class SpaceHeaterPackagesCalculatorService {

  private static final Map<Integer, RatingClass> RATING_THRESHOLDS;
  private static final Map<Integer, RatingClass> LOW_TEMPERATURE_HEAT_PUMP_RATING_THRESHOLDS;

  static {
    Map<Integer, RatingClass> thresholds = new LinkedHashMap<>();
    thresholds.put(150, RatingClass.APPP);
    thresholds.put(125, RatingClass.APP);
    thresholds.put(98, RatingClass.AP);
    thresholds.put(90, RatingClass.A);
    thresholds.put(82, RatingClass.B);
    thresholds.put(75, RatingClass.C);
    thresholds.put(36, RatingClass.D);
    thresholds.put(34, RatingClass.E);
    thresholds.put(30, RatingClass.F);
    thresholds.put(0, RatingClass.G);
    RATING_THRESHOLDS = Collections.unmodifiableMap(thresholds);

    Map<Integer, RatingClass> lowTemperatureHeatPumpThresholds = new LinkedHashMap<>();
    lowTemperatureHeatPumpThresholds.put(175, RatingClass.APPP);
    lowTemperatureHeatPumpThresholds.put(150, RatingClass.APP);
    lowTemperatureHeatPumpThresholds.put(123, RatingClass.AP);
    lowTemperatureHeatPumpThresholds.put(115, RatingClass.A);
    lowTemperatureHeatPumpThresholds.put(107, RatingClass.B);
    lowTemperatureHeatPumpThresholds.put(100, RatingClass.C);
    lowTemperatureHeatPumpThresholds.put(61, RatingClass.D);
    lowTemperatureHeatPumpThresholds.put(59, RatingClass.E);
    lowTemperatureHeatPumpThresholds.put(55, RatingClass.F);
    lowTemperatureHeatPumpThresholds.put(0, RatingClass.G);
    LOW_TEMPERATURE_HEAT_PUMP_RATING_THRESHOLDS = Collections.unmodifiableMap(lowTemperatureHeatPumpThresholds);
  }

  public RatingClass gePreferentialHeaterEfficiencyClass(SpaceHeaterPackagesCalculatorForm form) {
    Map<Integer, RatingClass> thresholdMap = thresholdMapSelector(form);
    int key = thresholdMap.keySet()
        .stream()
        .filter(integer -> getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form) * 100 >= integer)
        .findFirst()
        .orElse(0);

    return thresholdMap.get(key);
  }

  public RatingClass getPackageSpaceHeatingEfficiencyClass(SpaceHeaterPackagesCalculatorForm form) {
    Map<Integer, RatingClass> thresholdMap = thresholdMapSelector(form);
    int key = thresholdMap.keySet()
        .stream()
        .filter(integer -> getPackageSpaceHeatingEfficiencyDecimal(form) * 100 >= integer)
        .findFirst()
        .orElse(0);

    return thresholdMap.get(key);
  }

  public float getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return Float.parseFloat(form.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage()) / 100;
  }

  public float getPackageSpaceHeatingEfficiencyDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form) +
        getTemperatureControlEfficiencyDecimal(form) +
        getSupplementaryBoilerContributionDecimal(form) +
        getSolarContributionDecimal(form) +
        getSupplementaryHeatPumpContributionDecimal(form) -
        getSolarContributionAndHeatPumpDecimal(form);
  }

  public float getTemperatureControlEfficiencyDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return form.getHasTemperatureControl() ? TemperatureControlClass.valueOf(form.getTemperatureControlClass()).getClassValue() / 100 : 0;
  }

  public float getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyDecimal(SpaceHeaterPackagesCalculatorForm form) {
    if (!form.getHasSupplementaryBoiler()) {
      return 0;
    }
    return Float.parseFloat(form.getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage()) / 100;
  }

  public float getSupplementaryBoilerFactor(SpaceHeaterPackagesCalculatorForm form) {
    if (form.getPreferentialHeaterType() == PreferentialHeaterTypes.BOILER) {
      return 0.1F;
    } else if (!form.getHasSupplementaryBoiler()) {
      return 0;
    }

    float x = Float.parseFloat(form.getPreferentialHeaterHeatOutput()) /
        (Float.parseFloat(form.getPreferentialHeaterHeatOutput()) +
            Float.parseFloat(form.getSupplementaryBoilerHeatOutput()));

    // Figures from Table 6 of Annex IV of EU reg 811/2013
    if (form.getHasStorageTank()) {
      if (x <= 0.1F) {
        return interpolate(0, 0.1F, 1, 0.63F, x);
      } else if (x <= 0.2F) {
        return interpolate(0.1F, 0.2F, 0.63F, 0.3F, x);
      } else if (x <= 0.3F) {
        return this.interpolate(0.2F, 0.3F, 0.3F, 0.15F, x);
      } else if (x <= 0.4F) {
        return this.interpolate(0.3F, 0.4F, 0.15F, 0.06F, x);
      } else if (x <= 0.5F) {
        return this.interpolate(0.4F, 0.5F, 0.06F, 0.02F, x);
      } else if (x <= 0.6F) {
        return this.interpolate(0.5F, 0.6F, 0.02F, 0, x);
      } else if (x <= 0.7F) {
        return this.interpolate(0.6F, 0.7F, 0, 0, x);
      } else {
        return 0;
      }
    } else {
      if (x <= 0.1F) {
        return this.interpolate(0, 0.1F, 1, 0.7F, x);
      } else if (x <= 0.2F) {
        return this.interpolate(0.1F, 0.2F, 0.7F, 0.45F, x);
      } else if (x <= 0.3F) {
        return this.interpolate(0.2F, 0.3F, 0.45F, 0.25F, x);
      } else if (x <= 0.4F) {
        return this.interpolate(0.3F, 0.4F, 0.25F, 0.15F, x);
      } else if (x <= 0.5F) {
        return this.interpolate(0.4F, 0.5F, 0.15F, 0.05F, x);
      } else if (x <= 0.6F) {
        return this.interpolate(0.5F, 0.6F, 0.05F, 0.02F, x);
      } else if (x <= 0.7F) {
        return this.interpolate(0.6F, 0.7F, 0.02F, 0, x);
      } else {
        return 0;
      }
    }
  }

  public float getSupplementaryBoilerContributionDecimal(SpaceHeaterPackagesCalculatorForm form) {
    if (!form.getHasSupplementaryBoiler()) {
      return 0;
    }

    return (getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyDecimal(form) -
        getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)) *
        getSupplementaryBoilerFactor(form);
  }

  public float getSolarCollectorSizeFactor(SpaceHeaterPackagesCalculatorForm form) {
    return 294 / (11 * Float.parseFloat(form.getPreferentialHeaterHeatOutput()));
  }

  public float getTankVolumeFactor(SpaceHeaterPackagesCalculatorForm form) {
    return 115 / (11 * Float.parseFloat(form.getPreferentialHeaterHeatOutput()));
  }

  public float getStorageTankVolumeMetersCubed(SpaceHeaterPackagesCalculatorForm form) {
    if (!form.getHasStorageTank()) {
      return 0;
    }

    return Float.parseFloat(form.getStorageTankVolume()) / 1000;
  }

  private float getSolarContributionScalingFactor(SpaceHeaterPackagesCalculatorForm form) {
    switch (form.getPreferentialHeaterType()) {
      case BOILER:
        return 0.9F;
      case HEAT_PUMP:
        return 0.45F;
      case COGENERATION_HEATER:
        return 0.7F;
      default:
        throw new IllegalStateException(
            String.format("Cannot getSolarContributionScalingFactor for %s", form.getPreferentialHeaterType())
        );
    }
  }

  public float getSolarContributionDecimal(SpaceHeaterPackagesCalculatorForm form) {
    if (!form.getHasSolarCollector() || !form.getHasStorageTank()) {
      return 0;
    }

    return ((getSolarCollectorSizeFactor(form) * Float.parseFloat(form.getSolarCollectorSize()) +
        getTankVolumeFactor(form) * getStorageTankVolumeMetersCubed(form)) *
        getSolarContributionScalingFactor(form) *
        (Float.parseFloat(form.getSolarCollectorEfficiencyPercentage()) / 100) *
        TankLabelClass.getEnum(form.getStorageTankRating()).getRatingValue()) / 100;
  }

  public float getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyDecimal(BoilerPackagesCalculatorForm form) {
    return Float.parseFloat(form.getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage()) / 100;
  }

  public float getSupplementaryHeatPumpFactor(BoilerPackagesCalculatorForm form) {
    if (!form.getHasSupplementaryHeatPump()) {
      return 0;
    }

    float x = Float.parseFloat(form.getSupplementaryHeatPumpHeatOutput()) /
        (Float.parseFloat(form.getPreferentialHeaterHeatOutput()) +
            Float.parseFloat(form.getSupplementaryHeatPumpHeatOutput()));

    // Figures from Table 5 of Annex IV of EU reg 811/2013
    if (form.getHasStorageTank()) {
      if (x <= 0.1) {
        return this.interpolate(0, 0.1F, 0, 0.37F, x);
      } else if (x <= 0.2F) {
        return this.interpolate(0.1F, 0.2F, 0.37F, 0.7F, x);
      } else if (x <= 0.3F) {
        return this.interpolate(0.2F, 0.3F, 0.7F, 0.85F, x);
      } else if (x <= 0.4F) {
        return this.interpolate(0.3F, 0.4F, 0.85F, 0.94F, x);
      } else if (x <= 0.5F) {
        return this.interpolate(0.4F, 0.5F, 0.94F, 0.98F, x);
      } else if (x <= 0.6F) {
        return this.interpolate(0.5F, 0.6F, 0.98F, 1.0F, x);
      } else {
        return 1.0F;
      }
    } else {
      if (x <= 0.1F) {
        return this.interpolate(0, 0.1F, 0, 0.3F, x);
      } else if (x <= 0.2F) {
        return this.interpolate(0.1F, 0.2F, 0.3F, 0.55F, x);
      } else if (x <= 0.3F) {
        return this.interpolate(0.2F, 0.3F, 0.55F, 0.75F, x);
      } else if (x <= 0.4F) {
        return this.interpolate(0.3F, 0.4F, 0.75F, 0.85F, x);
      } else if (x <= 0.5F) {
        return this.interpolate(0.4F, 0.5F, 0.85F, 0.95F, x);
      } else if (x <= 0.6F) {
        return this.interpolate(0.5F, 0.6F, 0.95F, 0.98F, x);
      } else if (x <= 0.7F) {
        return this.interpolate(0.6F, 0.7F, 0.98F, 1.0F, x);
      } else {
        return 1.0F;
      }
    }
  }

  public float getSupplementaryHeatPumpContributionDecimal(SpaceHeaterPackagesCalculatorForm form) {
    if (!(form instanceof BoilerPackagesCalculatorForm)) {
      return 0;
    }

    BoilerPackagesCalculatorForm boilerForm = (BoilerPackagesCalculatorForm) form;

    if (!boilerForm.getHasSupplementaryHeatPump() || boilerForm.getPreferentialHeaterType() != PreferentialHeaterTypes.BOILER) {
      return 0;
    }

    return (getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyDecimal(boilerForm) -
        getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)) *
        getSupplementaryHeatPumpFactor(boilerForm);
  }

  public float getSolarContributionAndHeatPumpDecimal(SpaceHeaterPackagesCalculatorForm form) {
    if (form.getPreferentialHeaterType() != PreferentialHeaterTypes.BOILER) {
      return 0;
    }

    return 0.5F * Math.min(getSolarContributionDecimal(form), getSupplementaryHeatPumpContributionDecimal(form));
  }

  public float getLowTemperatureHeatEmitters(SpaceHeaterPackagesCalculatorForm form) {
    return getPackageSpaceHeatingEfficiencyDecimal(form) +
        0.5F * getSupplementaryHeatPumpFactor((BoilerPackagesCalculatorForm) form);
  }

  public float getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderDecimal(
      HeatPumpPackagesCalculatorForm form) {
    return Float.parseFloat(form.getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage()) / 100;
  }

  public float getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerDecimal(
      HeatPumpPackagesCalculatorForm form) {
    return Float.parseFloat(form.getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage()) / 100;
  }

  public float getPreferentialHeatPumpColderDifferenceDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return Math.abs(getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form) -
        getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderDecimal((HeatPumpPackagesCalculatorForm) form));
  }

  public float getPreferentialHeatPumpWarmerDifferenceDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return Math.abs(getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form) -
        getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerDecimal((HeatPumpPackagesCalculatorForm) form));
  }

  public float getPackageSpaceHeatingEfficiencyColderDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return getPackageSpaceHeatingEfficiencyDecimal(form) -
        getPreferentialHeatPumpColderDifferenceDecimal(form);
  }

  public float getPackageSpaceHeatingEfficiencyWarmerDecimal(SpaceHeaterPackagesCalculatorForm form) {
    return getPackageSpaceHeatingEfficiencyDecimal(form) +
        getPreferentialHeatPumpWarmerDifferenceDecimal(form);
  }

  // Given two ranges, A and B, this function takes an input number, finds its
  // position along range A, and returns the number at the equivalent position along range B.
  // For example, if range A is 0 -> 10 and range B is 50 -> 100, for input 5 the output is 75,
  // as 5 is halfway between 0 and 10 and 75 is halfway between 50 and 100.
  private float interpolate(float fromA, float toA, float fromB, float toB, float value) {
    float proportionOfRange = (value - fromA) / (toA - fromA);
    return fromB + (toB - fromB) * proportionOfRange;
  }

  private Map<Integer, RatingClass> thresholdMapSelector(SpaceHeaterPackagesCalculatorForm form) {
    if (form instanceof HeatPumpPackagesCalculatorForm) {
      HeatPumpPackagesCalculatorForm heatPumpForm = (HeatPumpPackagesCalculatorForm) form;
      if (heatPumpForm.getLowTemperatureHeatPump()) {
        return LOW_TEMPERATURE_HEAT_PUMP_RATING_THRESHOLDS;
      }
    }
    return RATING_THRESHOLDS;
  }
}
