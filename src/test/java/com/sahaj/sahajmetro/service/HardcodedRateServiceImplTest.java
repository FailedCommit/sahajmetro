package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

class HardcodedRateServiceImplTest {
    private final RateService rateService = new HardcodedRateServiceImpl();

    @Test
    void testTripRate_weekendMorningPeakHour() {
        Trip zoneOneTrip = new Trip(
                of(2021, 11, 6, 10, 15), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));
        Trip zoneTwoTrip = new Trip(
                of(2021, 11, 7, 9, 0), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip interZoneTrip1 = new Trip(
                of(2021, 11, 6, 11, 0), new Trip.CommuteStation(ZONE_ONE, ZONE_TWO));
        Trip interZoneTrip2 = new Trip(
                of(2021, 11, 7, 10, 59), new Trip.CommuteStation(ZONE_TWO, ZONE_ONE));

        final BigDecimal zoneOneToOneRate = rateService.tripRate(zoneOneTrip);
        final BigDecimal zoneTwoToTwoRate = rateService.tripRate(zoneTwoTrip);
        final BigDecimal crossZoneRate1 = rateService.tripRate(interZoneTrip1);
        final BigDecimal crossZoneRate2 = rateService.tripRate(interZoneTrip2);

        assertEquals(new BigDecimal("30"), zoneOneToOneRate);
        assertEquals(new BigDecimal("25"), zoneTwoToTwoRate);
        assertEquals(new BigDecimal("35"), crossZoneRate1);
        assertEquals(new BigDecimal("35"), crossZoneRate2);
    }

    @Test
    void testTripRate_weekdayEveningPeakHour() {
        Trip zoneOneTrip = new Trip(
                of(2021, 11, 2, 17, 0), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));
        Trip zoneTwoTrip = new Trip(
                of(2021, 11, 2, 20, 0), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip interZoneTrip1 = new Trip(
                of(2021, 11, 2, 17, 1), new Trip.CommuteStation(ZONE_ONE, ZONE_TWO));
        Trip interZoneTrip2 = new Trip(
                of(2021, 11, 2, 19, 59), new Trip.CommuteStation(ZONE_TWO, ZONE_ONE));

        final BigDecimal zoneOneToOneRate = rateService.tripRate(zoneOneTrip);
        final BigDecimal zoneTwoToTwoRate = rateService.tripRate(zoneTwoTrip);
        final BigDecimal crossZoneRate1 = rateService.tripRate(interZoneTrip1);
        final BigDecimal crossZoneRate2 = rateService.tripRate(interZoneTrip2);

        assertEquals(new BigDecimal("30"), zoneOneToOneRate);
        assertEquals(new BigDecimal("25"), zoneTwoToTwoRate);
        assertEquals(new BigDecimal("35"), crossZoneRate1);
        assertEquals(new BigDecimal("35"), crossZoneRate2);
    }

    @Test
    void testTripRate_weekendOffPeak() {
        Trip zoneOneTrip = new Trip(
                of(2021, 11, 6, 11, 1), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));
        Trip zoneTwoTrip = new Trip(
                of(2021, 11, 7, 22, 1), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip interZoneTrip1 = new Trip(
                of(2021, 11, 6, 12, 0), new Trip.CommuteStation(ZONE_ONE, ZONE_TWO));
        Trip interZoneTrip2 = new Trip(
                of(2021, 11, 7, 17, 59), new Trip.CommuteStation(ZONE_TWO, ZONE_ONE));

        final BigDecimal zoneOneToOneRate = rateService.tripRate(zoneOneTrip);
        final BigDecimal zoneTwoToTwoRate = rateService.tripRate(zoneTwoTrip);
        final BigDecimal crossZoneRate1 = rateService.tripRate(interZoneTrip1);
        final BigDecimal crossZoneRate2 = rateService.tripRate(interZoneTrip2);

        assertEquals(new BigDecimal("25"), zoneOneToOneRate);
        assertEquals(new BigDecimal("20"), zoneTwoToTwoRate);
        assertEquals(new BigDecimal("30"), crossZoneRate1);
        assertEquals(new BigDecimal("30"), crossZoneRate2);
    }

    @Test
    void testTripRate_weekdayOffPeak() {
        Trip zoneOneTrip = new Trip(
                of(2021, 11, 2, 6, 59), new Trip.CommuteStation(ZONE_ONE, ZONE_ONE));
        Trip zoneTwoTrip = new Trip(
                of(2021, 11, 3, 10, 31), new Trip.CommuteStation(ZONE_TWO, ZONE_TWO));
        Trip interZoneTrip1 = new Trip(
                of(2021, 11, 1, 14, 0), new Trip.CommuteStation(ZONE_ONE, ZONE_TWO));
        Trip interZoneTrip2 = new Trip(
                of(2021, 11, 4, 21, 59), new Trip.CommuteStation(ZONE_TWO, ZONE_ONE));

        final BigDecimal zoneOneToOneRate = rateService.tripRate(zoneOneTrip);
        final BigDecimal zoneTwoToTwoRate = rateService.tripRate(zoneTwoTrip);
        final BigDecimal crossZoneRate1 = rateService.tripRate(interZoneTrip1);
        final BigDecimal crossZoneRate2 = rateService.tripRate(interZoneTrip2);

        assertEquals(new BigDecimal("25"), zoneOneToOneRate);
        assertEquals(new BigDecimal("20"), zoneTwoToTwoRate);
        assertEquals(new BigDecimal("30"), crossZoneRate1);
        assertEquals(new BigDecimal("30"), crossZoneRate2);
    }
}