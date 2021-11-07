package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import com.sahaj.sahajmetro.model.enums.CappingMode;
import com.sahaj.sahajmetro.util.MetroUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.sahaj.sahajmetro.model.enums.CappingMode.DAILY;
import static com.sahaj.sahajmetro.model.enums.CappingMode.WEEKLY;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;
import static com.sahaj.sahajmetro.util.TripValidator.validateDailyTrips;
import static com.sahaj.sahajmetro.util.TripValidator.validateWeeklyTrips;

public class FareCappingServiceImpl implements FareCappingService {

    @Override
    public BigDecimal getDailyCapAmount(List<Trip> dailyTrips) {
        validateDailyTrips(dailyTrips);
        CommuteStation station = getCommuteStation(dailyTrips);
        return getCappedAmount(station, DAILY);
    }

    @Override
    public BigDecimal getWeeklyCapAmount(List<Trip> weeklyTrips) {
        validateWeeklyTrips(weeklyTrips);
        CommuteStation station = getCommuteStation(weeklyTrips);
        return getCappedAmount(station, WEEKLY);
    }

    private CommuteStation getCommuteStation(List<Trip> trips) {
        CommuteStation station = new CommuteStation(ZONE_TWO, ZONE_TWO);
        for (Trip trip : trips) {
            if (ZONE_ONE == trip.getStationDetails().getStartingZone() &&
                    ZONE_ONE == trip.getStationDetails().getEndingZone()) {
                station = new CommuteStation(ZONE_ONE, ZONE_ONE);
            } else if(ZONE_ONE == trip.getStationDetails().getStartingZone() && ZONE_TWO == trip.getStationDetails().getEndingZone()) {
                station = new CommuteStation(ZONE_ONE, ZONE_TWO);
                break;
            } else if(ZONE_TWO == trip.getStationDetails().getStartingZone() && ZONE_ONE == trip.getStationDetails().getEndingZone()) {
                station = new CommuteStation(ZONE_TWO, ZONE_ONE);
                break;
            }
        }
        return station;
    }

    private BigDecimal getCappedAmount(CommuteStation station, CappingMode mode) {
        if(DAILY == mode) {
            return MetroUtils.DAILY_CAP.get(station);
        }
        return MetroUtils.WEEKLY_CAP.get(station);
    }
}
