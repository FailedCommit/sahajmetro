package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class HardcodedRateServiceImpl implements RateService {
    @Override
    public BigDecimal tripRate(Trip trip) {
        if (ZONE_ONE == trip.getStationDetails().getStartingZone() &&
                ZONE_ONE == trip.getStationDetails().getEndingZone()) {
            if (isPeakHour(trip.getDateTime())) {
                return new BigDecimal("30");
            } else {
                return new BigDecimal("25");
            }
        } else if (ZONE_TWO == trip.getStationDetails().getStartingZone() &&
                ZONE_TWO == trip.getStationDetails().getEndingZone()) {
            if (isPeakHour(trip.getDateTime())) {
                return new BigDecimal("25");
            } else {
                return new BigDecimal("20");
            }
        } else {
            if (isPeakHour(trip.getDateTime())) {
                return new BigDecimal("35");
            } else {
                return new BigDecimal("30");
            }
        }
    }

    private boolean isPeakHour(LocalDateTime dateTime) {
        final DayOfWeek day = dateTime.getDayOfWeek();
        final LocalTime localTripTime = dateTime.toLocalTime();
        LocalTime weekdayMorningPeakStart = LocalTime.parse("07:00");
        LocalTime weekdayMorningPeakEnd = LocalTime.parse("10:30");
        LocalTime weekdayEveningPeakStart = LocalTime.parse("17:00");
        LocalTime weekdayEveningPeakEnd = LocalTime.parse("20:00");

        LocalTime weekendMorningPeakStart = LocalTime.parse("09:00");
        LocalTime weekendMorningPeakEnd = LocalTime.parse("11:00");
        LocalTime weekendEveningPeakStart = LocalTime.parse("18:00");
        LocalTime weekendEveningPeakEnd = LocalTime.parse("22:00");

        boolean morningPeak = false;
        boolean eveningPeak = false;

        if (day == SATURDAY || day == SUNDAY) {
            morningPeak = isTripTimeInRange(localTripTime, weekendMorningPeakStart, weekendMorningPeakEnd);
            eveningPeak = isTripTimeInRange(localTripTime, weekendEveningPeakStart, weekendEveningPeakEnd);

            return morningPeak || eveningPeak;
        } else {
            morningPeak = isTripTimeInRange(localTripTime, weekdayMorningPeakStart, weekdayMorningPeakEnd);
            eveningPeak = isTripTimeInRange(localTripTime, weekdayEveningPeakStart, weekdayEveningPeakEnd);

            return morningPeak || eveningPeak;
        }
    }

    private boolean isTripTimeInRange(LocalTime tripTime, LocalTime startTime, LocalTime endTime) {
        return tripTime.compareTo(startTime) == 0 ||
                tripTime.compareTo(endTime) == 0 ||
                (tripTime.isAfter(startTime) && tripTime.isBefore(endTime));
    }
}
