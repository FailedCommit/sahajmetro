package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.util.MetroUtils;
import com.sahaj.sahajmetro.util.TripsComparator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class FareCalculatorServiceImpl implements FareCalculatorService {
    private final FareCappingService fareCappingService = new FareCappingServiceImpl();
    private final RateService rateService = new HardcodedRateServiceImpl();

    @Override
    public BigDecimal calculate(List<Trip> trips) {
        if (isNull(trips) || trips.isEmpty()) {
            return BigDecimal.ZERO;
        }

        trips.sort(new TripsComparator());
        Map<LocalDate, List<Trip>> tripsByDate = tripsByDate(trips);
        final Map<Integer, Map<LocalDate, List<Trip>>> tripsByWeek = tripsByWeek(tripsByDate);
        return calculateTripFaresByWeek(tripsByWeek);
    }

    private BigDecimal calculateTripFaresByWeek(Map<Integer, Map<LocalDate, List<Trip>>> tripsByWeek) {
        BigDecimal totalFare = BigDecimal.ZERO;
        for(int weekNumber : tripsByWeek.keySet()) {
            BigDecimal weeklyFare = BigDecimal.ZERO;
            Map<LocalDate, List<Trip>> tripsByDate = tripsByWeek.get(weekNumber);
            List<Trip> weeklyTrips = tripsByDate.values().stream()
                    .flatMap(Collection::stream)
                    .sorted(new TripsComparator())
                    .collect(Collectors.toList());
            BigDecimal weeklyCap = fareCappingService.getWeeklyCapAmount(weeklyTrips);
            final Set<LocalDate> localDates = new TreeSet<>(tripsByDate.keySet());
            for(LocalDate date : localDates) {
                BigDecimal dailyFare = calculateTripFaresByDate(tripsByDate.get(date));
                if (freePassNotEnabled(dailyFare, weeklyCap)) {
                    if (weeklyCap.compareTo(weeklyFare.add(dailyFare)) > 0) {
                        weeklyFare = weeklyFare.add(dailyFare);
                    } else {
                        weeklyFare = weeklyCap;
                    }
                }
            }
            totalFare = totalFare.add(weeklyFare);
        }
        return totalFare;
    }

    private BigDecimal calculateTripFaresByDate(List<Trip> trips) {
        BigDecimal dailyFare = BigDecimal.ZERO;
        BigDecimal totalFare = BigDecimal.ZERO;
        BigDecimal dailyCap = fareCappingService.getDailyCapAmount(trips);
        for(Trip trip : trips) {
            if (freePassNotEnabled(dailyFare, dailyCap)) {
                final BigDecimal tripRate = rateService.tripRate(trip);
                if (dailyCap.compareTo(dailyFare.add(tripRate)) > 0) {
                    dailyFare = dailyFare.add(tripRate);
                } else {
                    dailyFare = dailyCap;
                }
            }
        }
        return totalFare.add(dailyFare);
    }

    private Map<LocalDate, List<Trip>> tripsByDate(List<Trip> trips) {
        return trips.stream().collect(Collectors.groupingBy(Trip::getDate));
    }

    private Map<Integer, Map<LocalDate, List<Trip>>> tripsByWeek(Map<LocalDate, List<Trip>> tripsByDate) {
        Map<Integer, Map<LocalDate, List<Trip>>> tripsByWeek = new TreeMap<>();
        for(LocalDate date : tripsByDate.keySet()) {
            final int weekNumberOfTheYear = MetroUtils.getWeekNumber(date);
            List<Trip> trips = tripsByDate.get(date);
            Map<LocalDate, List<Trip>> localDateListMap = new HashMap<>();
            if(tripsByWeek.containsKey(weekNumberOfTheYear)) {
                localDateListMap = tripsByWeek.get(weekNumberOfTheYear);
            }
            localDateListMap.put(date, trips);
            tripsByWeek.put(weekNumberOfTheYear, localDateListMap);
        }
        return tripsByWeek;
    }

    private boolean isFreePassEnabled(BigDecimal totalFare, BigDecimal cap) {
        return totalFare.compareTo(cap) >= 0;
    }

    private boolean freePassNotEnabled(BigDecimal totalFare, BigDecimal cap) {
        return !isFreePassEnabled(totalFare, cap);
    }
}
