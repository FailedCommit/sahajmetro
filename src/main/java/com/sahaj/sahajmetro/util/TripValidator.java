package com.sahaj.sahajmetro.util;

import com.sahaj.sahajmetro.SValidationException;
import com.sahaj.sahajmetro.model.Trip;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

import static com.sahaj.sahajmetro.Constants.*;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static com.sahaj.sahajmetro.util.MetroUtils.getWeekNumber;
import static java.util.Objects.isNull;

@UtilityClass
public class TripValidator {
    public static void validateDailyTrips(List<Trip> dailyTrips) {
        if (isNull(dailyTrips) || dailyTrips.isEmpty())
            throw new SValidationException(NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG);
        if (isMultiDate(dailyTrips)) throw new SValidationException(NON_UNIQUE_DAY_EXCEPTION_MSG);
        if (missingCommuteStations(dailyTrips)) throw new SValidationException(MISSING_COMMUTE_STATIONS_EXCEPTION_MSG);
    }

    public static void validateWeeklyTrips(List<Trip> weeklyTrips) {
        if (isNull(weeklyTrips) || weeklyTrips.isEmpty())
            throw new SValidationException(NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG);
        if (isMultiWeek(weeklyTrips)) throw new SValidationException(NON_UNIQUE_WEEK_EXCEPTION_MSG);
        if (missingCommuteStations(weeklyTrips)) throw new SValidationException(MISSING_COMMUTE_STATIONS_EXCEPTION_MSG);
    }

    private boolean isMultiDate(List<Trip> trips) {
        final LocalDate date = trips.stream().findFirst().get().getDate();
        return trips.stream().anyMatch(trip -> !trip.getDate().isEqual(date));
    }

    private boolean isMultiWeek(List<Trip> trips) {
        final LocalDate date = trips.stream().findFirst().get().getDate();
        final int weekNumber = getWeekNumber(date);
        return trips.stream().anyMatch(trip -> weekNumber != getWeekNumber(trip.getDate()));
    }

    private static boolean missingCommuteStations(List<Trip> trips) {
        for (Trip trip : trips) {
            if (trip.getStationDetails() == null ||
                    (ZONE_ONE != trip.getStationDetails().getStartingZone() && ZONE_TWO != trip.getStationDetails().getStartingZone()) ||
                    (ZONE_ONE != trip.getStationDetails().getEndingZone() && ZONE_TWO != trip.getStationDetails().getEndingZone())) {
                return true;
            }
        }
        return false;
    }
}
