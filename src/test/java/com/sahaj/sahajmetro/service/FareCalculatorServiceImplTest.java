package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import com.sahaj.sahajmetro.model.Trip.CommuteTime;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

class FareCalculatorServiceImplTest {

    private final FareCalculatorService fareCalculatorService = new FareCalculatorServiceImpl();

    @Test
    void testCalculate_singleDayFare() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip2 = new Trip(
                of(2021, 11, 1, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip3 = new Trip(
                of(2021, 11, 1, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip4 = new Trip(
                of(2021, 11, 1, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip5 = new Trip(
                of(2021, 11, 1, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        BigDecimal fare = fareCalculatorService.calculate(new ArrayList<>(List.of(trip1, trip2, trip3, trip4, trip5)));
        assertEquals(new BigDecimal("120"), fare);
    }

    @Test
    void testCalculate_singleWeekFare_noCap() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip2 = new Trip(
                of(2021, 11, 2, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip3 = new Trip(
                of(2021, 11, 3, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip4 = new Trip(
                of(2021, 11, 4, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip5 = new Trip(
                of(2021, 11, 5, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip6 = new Trip(
                of(2021, 11, 6, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip7 = new Trip(
                of(2021, 11, 7, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        BigDecimal fare = fareCalculatorService.calculate(new ArrayList<>(List.of(trip1, trip2, trip3, trip4, trip5, trip6, trip7)));
        assertEquals(new BigDecimal("235"), fare);
    }

    @Test
    void testCalculate_singleWeekFare_withCap() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip11 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip111 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip1111 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip2 = new Trip(
                of(2021, 11, 2, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip22 = new Trip(
                of(2021, 11, 2, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip222 = new Trip(
                of(2021, 11, 2, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip2222 = new Trip(
                of(2021, 11, 2, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip3 = new Trip(
                of(2021, 11, 3, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip33 = new Trip(
                of(2021, 11, 3, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip333 = new Trip(
                of(2021, 11, 3, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip3333 = new Trip(
                of(2021, 11, 3, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip4 = new Trip(
                of(2021, 11, 4, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip44 = new Trip(
                of(2021, 11, 4, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip444 = new Trip(
                of(2021, 11, 4, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip4444 = new Trip(
                of(2021, 11, 4, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip5 = new Trip(
                of(2021, 11, 5, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip55 = new Trip(
                of(2021, 11, 5, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip555 = new Trip(
                of(2021, 11, 5, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip5555 = new Trip(
                of(2021, 11, 5, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip6 = new Trip(
                of(2021, 11, 6, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip66 = new Trip(
                of(2021, 11, 6, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip666 = new Trip(
                of(2021, 11, 6, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));
        Trip trip6666 = new Trip(
                of(2021, 11, 6, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip7 = new Trip(
                of(2021, 11, 7, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        BigDecimal fare = fareCalculatorService.calculate(
                new ArrayList<>(List.of(
                        trip1, trip11, trip111, trip1111, // 100 daily cap
                        trip2, trip22, trip222, trip2222, // 100 daily cap
                        trip3, trip33, trip333, trip3333, // 100 daily cap
                        trip4, trip44, trip444, trip4444, // 100 daily cap
                        trip5, trip55, trip555, trip5555, // 100 daily cap
                        trip6, trip66, trip666, trip6666, // 100 daily cap
                        trip7)));                         // 600 weekly cap, this trip should be free
        assertEquals(new BigDecimal("600"), fare);
    }

    @Test
    void testCalculate_multiWeekFare() {
        Trip trip1 = new Trip(
                of(2021, 11, 4, 10, 20), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip2 = new Trip(
                of(2021, 11, 5, 10, 45), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip3 = new Trip(
                of(2021, 11, 10, 16, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip4 = new Trip(
                of(2021, 11, 11, 18, 15), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip5 = new Trip(
                of(2021, 11, 25, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip6 = new Trip(
                of(2021, 11, 26, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        Trip trip7 = new Trip(
                of(2021, 11, 27, 19, 00), new CommuteStation(ZONE_TWO, ZONE_ONE));

        BigDecimal fare = fareCalculatorService.calculate(new ArrayList<>(List.of(trip1, trip2, trip3, trip4, trip5, trip6, trip7)));
        assertEquals(new BigDecimal("235"), fare);
    }

    @Test
    void testCalculate_multiWeekFareWithCapping() {
        List<Trip> monthlyTripData = new ArrayList<>();
        for(int i = 1; i < 31; i++) {
            Trip trip1 = new Trip(
                    of(2021, 11, i, 10, 00), new CommuteStation(ZONE_ONE, ZONE_ONE));
            Trip trip2 = new Trip(
                    of(2021, 11, i, 10, 00), new CommuteStation(ZONE_ONE, ZONE_ONE));
            Trip trip3 = new Trip(
                    of(2021, 11, i, 10, 00), new CommuteStation(ZONE_ONE, ZONE_ONE));
            Trip trip4 = new Trip(
                    of(2021, 11, i, 10, 00), new CommuteStation(ZONE_ONE, ZONE_ONE));

            monthlyTripData.add(trip1);
            monthlyTripData.add(trip2);
            monthlyTripData.add(trip3);
            monthlyTripData.add(trip4);

        }
        BigDecimal fare = fareCalculatorService.calculate(monthlyTripData);
        assertEquals(new BigDecimal("2200"), fare);
    }
}