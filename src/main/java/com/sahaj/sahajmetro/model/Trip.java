package com.sahaj.sahajmetro.model;

import com.sahaj.sahajmetro.model.enums.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;

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
//    private CommuteTime timeDetails; // Ensure NotNull
    private CommuteStation stationDetails; // Ensure NotNull

    /** Date of journey without time units */
    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    /** Week of a given year */
    public int getWeek() {
        return this.dateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    @Data
    @AllArgsConstructor
    public static class CommuteStation {
        private Zone startingZone; // Ensure NotNull
        private Zone endingZone; // Ensure NotNull
    }

    @Data
    @AllArgsConstructor
    public static class CommuteTime {
        private DayOfWeek day;
        private LocalTime time;
    }
}
