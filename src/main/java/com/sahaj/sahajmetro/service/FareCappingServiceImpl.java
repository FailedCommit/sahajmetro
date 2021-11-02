package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.Trip.CommuteStation;
import com.sahaj.sahajmetro.model.enums.CappingMode;
import com.sahaj.sahajmetro.util.MetroUtils;
import com.sahaj.sahajmetro.util.TripsComparator;
import com.sun.source.tree.Tree;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.sahaj.sahajmetro.model.enums.CappingMode.DAILY;
import static com.sahaj.sahajmetro.model.enums.CappingMode.WEEKLY;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_ONE;
import static com.sahaj.sahajmetro.model.enums.Zone.ZONE_TWO;

public class FareCappingServiceImpl implements FareCappingService {

    @Override
    public BigDecimal getDailyCapAmount(List<Trip> trips) {
        CommuteStation station = getCommuteStation(trips);
        return getCappedAmount(station, DAILY);
    }

    @Override
    public BigDecimal getWeeklyCapAmount(List<Trip> trips) {
        CommuteStation station = getCommuteStation(trips);
        return getCappedAmount(station, WEEKLY);
    }

    private CommuteStation getCommuteStation(List<Trip> trips) {
        CommuteStation station = new CommuteStation(ZONE_TWO, ZONE_TWO);
        for (Trip trip : trips) {
            if (ZONE_ONE == trip.getStationDetails().getStartingZone() &&
                    ZONE_ONE == trip.getStationDetails().getEndingZone()) {
                station = new CommuteStation(ZONE_ONE, ZONE_ONE);
            }
            else if(ZONE_ONE == trip.getStationDetails().getStartingZone() && ZONE_TWO == trip.getStationDetails().getEndingZone()) {
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
