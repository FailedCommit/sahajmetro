package com.sahaj.sahajmetro.model;

import com.sahaj.sahajmetro.model.enums.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;

@Data
@AllArgsConstructor
public class Trip {
    private LocalDateTime dateTime;
    private CommuteStation stationDetails;

    /** Date of journey without time units */
    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    @Data
    @AllArgsConstructor
    public static class CommuteStation {
        private Zone startingZone;
        private Zone endingZone;
    }

    @Data
    @AllArgsConstructor
    public static class CommuteTime {
        private DayOfWeek day;
        private LocalTime time;
    }
}
