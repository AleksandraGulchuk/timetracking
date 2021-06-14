package com.epam.timetracking.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Time class represents the holder for seconds in numeric.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Time implements Comparable<Time> {
    private Long time = 0L;

    private static final int DAY = 86400;
    private static final int HOUR = 3600;
    private static final int MINUTE = 60;

    /**
     * Parsers a String object to a Time object.
     *
     * @param timeAsString - a String object to parse.
     *                     Must match next patterns: \d{2}:\d{2}:\d{2} or \d{2}:\d{2}
     *
     * @return - a parsed Time object.
     */
    public static Time parse(String timeAsString) {
        if (timeAsString == null || timeAsString.length() == 0) {
            return new Time();
        }
        String[] times = timeAsString.split(":");
        long result = 0L;
        if(times.length == 2) {
            result = Long.parseLong(times[0]) * HOUR +
                    Long.parseLong(times[1]) * MINUTE;
        }
        if(times.length == 3){
            result = Long.parseLong(times[0]) * DAY +
                    Long.parseLong(times[1]) * HOUR +
                    Long.parseLong(times[2]) * MINUTE;
        }
        return new Time(result);
    }

    /**
     * Adds a Time object to this object.
     *
     * @param time - Time object to add.
     */
    public void append(Time time) {
        this.time += time.time;
    }

    /**
     * Finds the sum of two Time objects.
     *
     * @param time1 - first addend.
     * @param time2 - second addend.
     *
     * @return - the total sum of Time objects.
     */
    public static Time sum(Time time1, Time time2) {
        return new Time(time1.time + time2.time);
    }

    @Override
    public int compareTo(Time t) {
        return this.time.compareTo(t.time);
    }

    @Override
    public String toString() {
        if (time == 0) {
            return "00:00:00";
        }
        long days = time / DAY;
        long hours = (time - days * DAY) / HOUR;
        long minutes = (time - days * DAY - hours * HOUR) / MINUTE;
        return getString(days) + ":" +
                getString(hours) + ":" +
                getString(minutes);
    }

    private String getString(long value) {
        if (value < 10) {
            return "0" + value;
        }
        return String.valueOf(value);
    }
}
