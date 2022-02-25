package uk.gov.beis.els.categories.waterheaters.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.waterheaters.model.WaterHeaterPackageCalculatorForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.model.RatingClass;

@Service
public class WaterSolarPackagesCalculatorService {

  // This mapping of load profile to number is used in a couple of the calculations below.
  // Qref values are taken from Table 3 in https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=celex%3A32013R0812
  // Rating classes are from Annex II table 1
  private static final Map<LoadProfile, Map<Integer, RatingClass>> LOAD_PROFILE_VALUES;
  private static final Map<LoadProfile, Float> LOAD_PROFILE_QREF_VALUES;

  static {
    Map<Integer, RatingClass> xxxsRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xxsRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xsRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> sRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> mRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> lRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xlRatingClasses = new LinkedHashMap<>();
    Map<Integer, RatingClass> xxlRatingClasses = new LinkedHashMap<>();

    xxxsRatingClasses.put(62, RatingClass.APPP);
    xxxsRatingClasses.put(53, RatingClass.APP);
    xxxsRatingClasses.put(44, RatingClass.AP);
    xxxsRatingClasses.put(35, RatingClass.A);
    xxxsRatingClasses.put(32, RatingClass.B);
    xxxsRatingClasses.put(29, RatingClass.C);
    xxxsRatingClasses.put(26, RatingClass.D);
    xxxsRatingClasses.put(22, RatingClass.E);
    xxxsRatingClasses.put(19, RatingClass.F);
    xxxsRatingClasses.put(0, RatingClass.G);

    xxsRatingClasses.put(62, RatingClass.APPP);
    xxsRatingClasses.put(53, RatingClass.APP);
    xxsRatingClasses.put(44, RatingClass.AP);
    xxsRatingClasses.put(35, RatingClass.A);
    xxsRatingClasses.put(32, RatingClass.B);
    xxsRatingClasses.put(29, RatingClass.C);
    xxsRatingClasses.put(26, RatingClass.D);
    xxsRatingClasses.put(23, RatingClass.E);
    xxsRatingClasses.put(20, RatingClass.F);
    xxsRatingClasses.put(0, RatingClass.G);

    xsRatingClasses.put(69, RatingClass.APPP);
    xsRatingClasses.put(61, RatingClass.APP);
    xsRatingClasses.put(53, RatingClass.AP);
    xsRatingClasses.put(38, RatingClass.A);
    xsRatingClasses.put(35, RatingClass.B);
    xsRatingClasses.put(32, RatingClass.C);
    xsRatingClasses.put(29, RatingClass.D);
    xsRatingClasses.put(26, RatingClass.E);
    xsRatingClasses.put(23, RatingClass.F);
    xsRatingClasses.put(0, RatingClass.G);

    sRatingClasses.put(90, RatingClass.APPP);
    sRatingClasses.put(72, RatingClass.APP);
    sRatingClasses.put(55, RatingClass.AP);
    sRatingClasses.put(38, RatingClass.A);
    sRatingClasses.put(35, RatingClass.B);
    sRatingClasses.put(32, RatingClass.C);
    sRatingClasses.put(29, RatingClass.D);
    sRatingClasses.put(26, RatingClass.E);
    sRatingClasses.put(23, RatingClass.F);
    sRatingClasses.put(0, RatingClass.G);

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
    aMap.put(LoadProfile.XXXS, Collections.unmodifiableMap(xxxsRatingClasses));
    aMap.put(LoadProfile.XXS, Collections.unmodifiableMap(xxsRatingClasses));
    aMap.put(LoadProfile.XS, Collections.unmodifiableMap(xsRatingClasses));
    aMap.put(LoadProfile.S, Collections.unmodifiableMap(sRatingClasses));
    aMap.put(LoadProfile.M, Collections.unmodifiableMap(mRatingClasses));
    aMap.put(LoadProfile.L, Collections.unmodifiableMap(lRatingClasses));
    aMap.put(LoadProfile.XL, Collections.unmodifiableMap(xlRatingClasses));
    aMap.put(LoadProfile.XXL, Collections.unmodifiableMap(xxlRatingClasses));
    LOAD_PROFILE_VALUES = Collections.unmodifiableMap(aMap);

    Map<LoadProfile, Float> qrefMap = new LinkedHashMap<>();
    qrefMap.put(LoadProfile.XXXS, 0.345F);
    qrefMap.put(LoadProfile.XXS, 2.1F);
    qrefMap.put(LoadProfile.XS, 2.1F);
    qrefMap.put(LoadProfile.S, 2.1F);
    qrefMap.put(LoadProfile.M, 5.845F);
    qrefMap.put(LoadProfile.L, 11.655F);
    qrefMap.put(LoadProfile.XL, 19.07F);
    qrefMap.put(LoadProfile.XXL, 24.53F);
    LOAD_PROFILE_QREF_VALUES = Collections.unmodifiableMap(qrefMap);
  }

  public RatingClass getWaterHeatingEfficiencyClass(WaterHeaterPackageCalculatorForm form) {
    LoadProfile declaredLoadProfile = LoadProfile.getEnum(form.getDeclaredLoadProfile());
    Integer key = LOAD_PROFILE_VALUES.get(declaredLoadProfile).keySet()
        .stream()
        .filter(integer -> Integer.parseInt(form.getWaterHeatingEfficiencyPercentage()) >= integer)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Cannot get water heating efficiency class"));

    return LOAD_PROFILE_VALUES.get(declaredLoadProfile).get(key);
  }

  public RatingClass getPackageWaterHeatingEfficiencyClass(WaterHeaterPackageCalculatorForm form) {
    LoadProfile loadProfile = LoadProfile.getEnum(form.getDeclaredLoadProfile());
    Integer key = LOAD_PROFILE_VALUES.get(loadProfile).keySet()
        .stream()
        .filter(integer -> getPackageWaterHeatingEfficiencyDecimal(form) * 100 >= integer)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Cannot get package water heating efficiency class"));

    return LOAD_PROFILE_VALUES.get(loadProfile).get(key);
  }

  public float getPackageWaterHeatingEfficiencyDecimal(WaterHeaterPackageCalculatorForm form) {
    return getWaterHeatingEfficiencyDecimal(form) + getSolarContributionDecimal(form);
  }

  public float getWaterHeatingEfficiencyDecimal(WaterHeaterPackageCalculatorForm form) {
    return Float.parseFloat(form.getWaterHeatingEfficiencyPercentage()) / 100F;
  }

  public float getSolarContributionDecimal(WaterHeaterPackageCalculatorForm form) {
    return (1.1F * getWaterHeatingEfficiencyDecimal(form) - 0.1F) *
        ((220F * LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()))) /
            Float.parseFloat(form.getAnnualNonSolarHeatContribution())) -
        getAuxElectricityConsumptionProportionDecimal(form) - getWaterHeatingEfficiencyDecimal(form);

  }

  public float getAuxElectricityConsumptionProportionDecimal(WaterHeaterPackageCalculatorForm form) {
    return (Float.parseFloat(form.getAuxElectricityConsumption()) * 2.5F) / 220F /
        LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()));
  }

  public float getNonSolarScalingFactor(WaterHeaterPackageCalculatorForm form) {
    return ((220F * LOAD_PROFILE_QREF_VALUES.get(LoadProfile.getEnum(form.getDeclaredLoadProfile()))) /
        Float.parseFloat(form.getAnnualNonSolarHeatContribution()));
  }

  public float getPackageWaterHeatingEfficiencyColderDecimal(WaterHeaterPackageCalculatorForm form) {
    return getPackageWaterHeatingEfficiencyDecimal(form) -
        0.2F * getSolarContributionDecimal(form);
  }

  public float getPackageWaterHeatingEfficiencyWarmerDecimal(WaterHeaterPackageCalculatorForm form) {
    return getPackageWaterHeatingEfficiencyDecimal(form) +
        0.4F * getSolarContributionDecimal(form);
  }
}
