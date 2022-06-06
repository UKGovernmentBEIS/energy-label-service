package uk.gov.beis.els.categories.waterheaters.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;
import uk.gov.beis.els.model.RatingClass;

@Service
public class WaterSolarPackagesCalculatorService {

  // Qref values are taken from Table 3 in https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=celex%3A32013R0812
  // Rating classes are from Annex II table 1
  private static final Map<LoadProfile, Map<Integer, RatingClass>> LOAD_PROFILE_VALUES;
  private static final Map<LoadProfile, Float> LOAD_PROFILE_QREF_VALUES;

  static {
    Map<Integer, RatingClass> mRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> lRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xlRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xxlRatingClasses = new LinkedHashMap<>();

    mRatingClasses.put(163, RatingClass.APPP);
    mRatingClasses.put(130, RatingClass.APP);
    mRatingClasses.put(100, RatingClass.AP);
    mRatingClasses.put(65, RatingClass.A);
    mRatingClasses.put(39, RatingClass.B);
    mRatingClasses.put(36, RatingClass.C);
    mRatingClasses.put(33, RatingClass.D);
    mRatingClasses.put(30, RatingClass.E);
    mRatingClasses.put(27, RatingClass.F);
    mRatingClasses.put(0, RatingClass.G);

    lRatingClasses.put(188, RatingClass.APPP);
    lRatingClasses.put(150, RatingClass.APP);
    lRatingClasses.put(115, RatingClass.AP);
    lRatingClasses.put(75, RatingClass.A);
    lRatingClasses.put(50, RatingClass.B);
    lRatingClasses.put(37, RatingClass.C);
    lRatingClasses.put(34, RatingClass.D);
    lRatingClasses.put(30, RatingClass.E);
    lRatingClasses.put(27, RatingClass.F);
    lRatingClasses.put(0, RatingClass.G);

    xlRatingClasses.put(200, RatingClass.APPP);
    xlRatingClasses.put(160, RatingClass.APP);
    xlRatingClasses.put(123, RatingClass.AP);
    xlRatingClasses.put(80, RatingClass.A);
    xlRatingClasses.put(55, RatingClass.B);
    xlRatingClasses.put(38, RatingClass.C);
    xlRatingClasses.put(35, RatingClass.D);
    xlRatingClasses.put(30, RatingClass.E);
    xlRatingClasses.put(27, RatingClass.F);
    xlRatingClasses.put(0, RatingClass.G);

    xxlRatingClasses.put(213, RatingClass.APPP);
    xxlRatingClasses.put(170, RatingClass.APP);
    xxlRatingClasses.put(131, RatingClass.AP);
    xxlRatingClasses.put(85, RatingClass.A);
    xxlRatingClasses.put(60, RatingClass.B);
    xxlRatingClasses.put(40, RatingClass.C);
    xxlRatingClasses.put(36, RatingClass.D);
    xxlRatingClasses.put(32, RatingClass.E);
    xxlRatingClasses.put(28, RatingClass.F);
    xxlRatingClasses.put(0, RatingClass.G);

    Map<LoadProfile, Map<Integer, RatingClass>> aMap = new LinkedHashMap<>();
    aMap.put(LoadProfile.M, Collections.unmodifiableMap(mRatingClasses));
    aMap.put(LoadProfile.L, Collections.unmodifiableMap(lRatingClasses));
    aMap.put(LoadProfile.XL, Collections.unmodifiableMap(xlRatingClasses));
    aMap.put(LoadProfile.XXL, Collections.unmodifiableMap(xxlRatingClasses));
    LOAD_PROFILE_VALUES = Collections.unmodifiableMap(aMap);

    Map<LoadProfile, Float> qrefMap = new LinkedHashMap<>();
    qrefMap.put(LoadProfile.M, 5.845F);
    qrefMap.put(LoadProfile.L, 11.655F);
    qrefMap.put(LoadProfile.XL, 19.07F);
    qrefMap.put(LoadProfile.XXL, 24.53F);
    LOAD_PROFILE_QREF_VALUES = Collections.unmodifiableMap(qrefMap);
  }

  public RatingClass getWaterHeatingEfficiencyClass(WaterSolarPackagesCalculatorForm form) {
    LoadProfile declaredLoadProfile = LoadProfile.getEnum(form.getDeclaredLoadProfile());
    Integer key = LOAD_PROFILE_VALUES.get(declaredLoadProfile).keySet()
        .stream()
        .filter(integer -> Integer.parseInt(form.getWaterHeatingEfficiencyPercentage()) >= integer)
        .findFirst()
        .orElse(0);

    return LOAD_PROFILE_VALUES.get(declaredLoadProfile).get(key);
  }

  public RatingClass getPackageWaterHeatingEfficiencyClass(WaterSolarPackagesCalculatorForm form) {
    LoadProfile loadProfile = LoadProfile.getEnum(form.getDeclaredLoadProfile());
    Integer key = LOAD_PROFILE_VALUES.get(loadProfile).keySet()
        .stream()
        .filter(integer -> getPackageWaterHeatingEfficiencyDecimal(form) * 100 >= integer)
        .findFirst()
        .orElse(0);

    return LOAD_PROFILE_VALUES.get(loadProfile).get(key);
  }

  public float getPackageWaterHeatingEfficiencyDecimal(WaterSolarPackagesCalculatorForm form) {
    return getWaterHeatingEfficiencyDecimal(form) + getSolarContributionDecimal(form);
  }

  public float getWaterHeatingEfficiencyDecimal(WaterSolarPackagesCalculatorForm form) {
    return Float.parseFloat(form.getWaterHeatingEfficiencyPercentage()) / 100F;
  }

  public float getSolarContributionDecimal(WaterSolarPackagesCalculatorForm form) {
    return (1.1F * getWaterHeatingEfficiencyDecimal(form) - 0.1F) *
        ((220F * LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()))) /
            Float.parseFloat(form.getAnnualNonSolarHeatContribution())) -
        getAuxElectricityConsumptionProportionDecimal(form) - getWaterHeatingEfficiencyDecimal(form);

  }

  public float getAuxElectricityConsumptionProportionDecimal(WaterSolarPackagesCalculatorForm form) {
    return (Float.parseFloat(form.getAuxElectricityConsumption()) * 2.5F) / 220F /
        LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()));
  }

  public float getNonSolarScalingFactor(WaterSolarPackagesCalculatorForm form) {
    return ((220F * LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()))) /
        Float.parseFloat(form.getAnnualNonSolarHeatContribution()));
  }

  public float getPackageWaterHeatingEfficiencyColderDecimal(WaterSolarPackagesCalculatorForm form) {
    return getPackageWaterHeatingEfficiencyDecimal(form) -
        0.2F * getSolarContributionDecimal(form);
  }

  public float getPackageWaterHeatingEfficiencyWarmerDecimal(WaterSolarPackagesCalculatorForm form) {
    return getPackageWaterHeatingEfficiencyDecimal(form) +
        0.4F * getSolarContributionDecimal(form);
  }
}
