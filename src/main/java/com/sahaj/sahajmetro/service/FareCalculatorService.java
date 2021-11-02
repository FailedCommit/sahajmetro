package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface FareCalculatorService {
    BigDecimal calculate(List<Trip> trips);
}
