package com.sahaj.sahajmetro.service;

import com.sahaj.sahajmetro.util.MetroUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetroUtilsTest {
    @Test
    public void testGetWeekNumber() {
        assertEquals(0, MetroUtils.getWeekNumber(of(2021, 1, 1)));
        assertEquals(44, MetroUtils.getWeekNumber(of(2021, 11, 1)));
        assertEquals(52, MetroUtils.getWeekNumber(of(2021, 12, 27)));
    }
}
