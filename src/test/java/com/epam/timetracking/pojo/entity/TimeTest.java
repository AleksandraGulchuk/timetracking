package com.epam.timetracking.pojo.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void testParse() {
        Time time = Time.parse("01:00");
        Assertions.assertEquals(new Time(3600L), time);
    }

    @Test
    public void testParseNull() {
        Time time = Time.parse(null);
        Assertions.assertEquals(new Time(), time);
    }

    @Test
    public void testParseDayTime() {
        Time time = Time.parse("00:01:00");
        Assertions.assertEquals(new Time(3600L), time);
    }

    @Test
    public void testAppend() {
        Time time = new Time(3600L);
        time.append(new Time(400L));
        Assertions.assertEquals(new Time(4000L), time);
    }

    @Test
    public void testSum() {
        Time time1 = new Time(3600L);
        Time time2 = new Time(400L);
        Assertions.assertEquals(new Time(4000L), Time.sum(time1, time2));
    }

    @Test
    public void testCompareTo() {
        Time time1 = new Time(3600L);
        Time time2 = new Time(400L);
        Assertions.assertTrue(time1.compareTo(time2) > 0);
    }

    @Test
    public void testToString() {
        Time time1 = new Time(43200L);
        Assertions.assertEquals("00:12:00", time1.toString());
        Time time2 = new Time();
        Assertions.assertEquals("00:00:00", time2.toString());
    }
}
