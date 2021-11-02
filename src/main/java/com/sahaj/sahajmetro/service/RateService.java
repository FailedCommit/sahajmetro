package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.model.Trip;

import java.math.BigDecimal;

public interface RateService {
    BigDecimal tripRate(Trip trip);
}
