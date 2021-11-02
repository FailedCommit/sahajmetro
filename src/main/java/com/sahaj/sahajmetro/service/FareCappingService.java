package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;
import com.sahaj.sahajmetro.model.enums.CappingMode;

import java.math.BigDecimal;
import java.util.List;

public interface FareCappingService {
    BigDecimal getDailyCapAmount(List<Trip> trips);
    BigDecimal getWeeklyCapAmount(List<Trip> trips);
}
