package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.SValidationException;
import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import org.junit.jupiter.api.Test;
import java.util.List;
import static com.sahaj.sahajmetro.Constants.*;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static com.sahaj.sahajmetro.util.TripValidator.validateDailyTrips;
import static com.sahaj.sahajmetro.util.TripValidator.validateWeeklyTrips;
import static java.time.LocalDateTime.of;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.*;

public class TripValidatorTest {
    @Test
    public void testValidateDailyTrips_sucess() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip2 = new Trip(
                of(2021, 11, 1, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip3 = new Trip(
                of(2021, 11, 1, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip4 = new Trip(
                of(2021, 11, 1, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip5 = new Trip(
                of(2021, 11, 1, 19, 0), new CommuteStation(ZONE_TWO, ZONE_ONE));

        assertDoesNotThrow(() -> validateDailyTrips(List.of(trip1, trip2, trip3, trip4, trip5)));
    }

    @Test
    public void testValidateDailyTrips_fail() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip2 = new Trip(
                of(2021, 11, 2, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip3 = new Trip(
                of(2021, 11, 2, 10, 20), new CommuteStation(null, ZONE_ONE));

        SValidationException nullOrEmptyValidationException = assertThrows(
                SValidationException.class,
                () -> validateDailyTrips(EMPTY_LIST),
                NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG
        );

        SValidationException multipleDaysFoundForSingleDayTrips = assertThrows(
                SValidationException.class,
                () -> validateDailyTrips(List.of(trip1, trip2)),
                NON_UNIQUE_DAY_EXCEPTION_MSG
        );

        SValidationException incorrectCommuteStationInTrips = assertThrows(
                SValidationException.class,
                () -> validateDailyTrips(List.of(trip3)),
                MISSING_COMMUTE_STATIONS_EXCEPTION_MSG
        );

        assertEquals(NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG, nullOrEmptyValidationException.getMessage());
        assertEquals(NON_UNIQUE_DAY_EXCEPTION_MSG, multipleDaysFoundForSingleDayTrips.getMessage());
        assertEquals(MISSING_COMMUTE_STATIONS_EXCEPTION_MSG, incorrectCommuteStationInTrips.getMessage());
    }

    @Test
    public void testValidateWeeklyTrips_success() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip2 = new Trip(
                of(2021, 11, 7, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        assertDoesNotThrow(() -> validateWeeklyTrips(List.of(trip1, trip2)));
    }

    @Test
    public void testValidateWeeklyTrips_fail() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip2 = new Trip(
                of(2021, 12, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip3 = new Trip(
                of(2021, 12, 1, 10, 20), null);

        SValidationException nullOrEmptyValidationException = assertThrows(
                SValidationException.class,
                () -> validateWeeklyTrips(EMPTY_LIST),
                NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG
        );

        SValidationException multipleDaysFoundForSingleDayTrips = assertThrows(
                SValidationException.class,
                () -> validateWeeklyTrips(List.of(trip1, trip2)),
                NON_UNIQUE_WEEK_EXCEPTION_MSG
        );

        SValidationException incorrectCommuteStationInTrips = assertThrows(
                SValidationException.class,
                () -> validateWeeklyTrips(List.of(trip3)),
                MISSING_COMMUTE_STATIONS_EXCEPTION_MSG
        );

        assertEquals(NULL_OR_EMPTY_TRIPS_EXCEPTION_MSG, nullOrEmptyValidationException.getMessage());
        assertEquals(NON_UNIQUE_WEEK_EXCEPTION_MSG, multipleDaysFoundForSingleDayTrips.getMessage());
        assertEquals(MISSING_COMMUTE_STATIONS_EXCEPTION_MSG, incorrectCommuteStationInTrips.getMessage());
    }
}
