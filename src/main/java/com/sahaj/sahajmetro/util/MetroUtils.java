package com.sahaj.sahajmetro.util;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;

@UtilityClass
public class MetroUtils {
    public static final Map<CommuteStation, BigDecimal> DAILY_CAP = Map.of(
            new CommuteStation(ZONE_ONE, ZONE_ONE), new BigDecimal("100"),
            new CommuteStation(ZONE_TWO, ZONE_TWO), new BigDecimal("80"),
            new CommuteStation(ZONE_ONE, ZONE_TWO), new BigDecimal("120"),
            new CommuteStation(ZONE_TWO, ZONE_ONE), new BigDecimal("120")
    );

    public static final Map<CommuteStation, BigDecimal> WEEKLY_CAP = Map.of(
            new CommuteStation(ZONE_ONE, ZONE_ONE), new BigDecimal("500"),
            new CommuteStation(ZONE_TWO, ZONE_TWO), new BigDecimal("400"),
            new CommuteStation(ZONE_ONE, ZONE_TWO), new BigDecimal("600"),
            new CommuteStation(ZONE_TWO, ZONE_ONE), new BigDecimal("600")
    );

    public static int getWeekNumber(LocalDate date) {
        return date.get(WeekFields.of(Locale.FRANCE).weekOfYear());
    }
}
