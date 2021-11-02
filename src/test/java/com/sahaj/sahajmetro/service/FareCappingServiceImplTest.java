package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import com.sahaj.sahajmetro.model.Trip.CommuteTime;
import com.sahaj.sahajmetro.model.enums.CappingMode;
import com.sahaj.sahajmetro.model.enums.Zone;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.sahaj.sahajmetro.model.enums.CappingMode.DAILY;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
class FareCappingServiceImplTest {
    private final static FareCappingService fareCappingService = new FareCappingServiceImpl();

    @Test
    void testGetCapAmount_crossZone() {
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

        BigDecimal dailyCappedAmount = fareCappingService.getDailyCapAmount(List.of(trip1, trip2, trip3, trip4, trip5));
        BigDecimal weeklyCappedAmount = fareCappingService.getWeeklyCapAmount(List.of(trip1, trip2, trip3, trip4, trip5));
        assertEquals(new BigDecimal("120"), dailyCappedAmount);
        assertEquals(new BigDecimal("600"), weeklyCappedAmount);
    }

    @Test
    void testGetCapAmount_ZoneOneToOne() {
        Trip trip3 = new Trip(
                of(2021, 11, 1, 16, 15), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip trip2 = new Trip(
                of(2021, 11, 1, 10, 45), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));

        BigDecimal dailyCapAmount = fareCappingService.getDailyCapAmount(List.of(trip1, trip2, trip3));
        BigDecimal weeklyCapAmount = fareCappingService.getWeeklyCapAmount(List.of(trip1, trip2, trip3));
        assertEquals(new BigDecimal("100"), dailyCapAmount);
        assertEquals(new BigDecimal("500"), weeklyCapAmount);
    }

    @Test
    void testGetCapAmount_ZoneTwoToTwo() {
        Trip trip1 = new Trip(
                of(2021, 11, 1, 10, 20), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip trip2 = new Trip(
                of(2021, 11, 1, 10, 45), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));

        BigDecimal dailyCapAmount = fareCappingService.getDailyCapAmount(List.of(trip1, trip2));
        BigDecimal weeklyCapAmount = fareCappingService.getWeeklyCapAmount(List.of(trip1, trip2));
        assertEquals(new BigDecimal("80"), dailyCapAmount);
        assertEquals(new BigDecimal("400"), weeklyCapAmount);
    }
}