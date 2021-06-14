package com.epam.timetracking.controller.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaginationTest {

    @Test
    public void testGetBeginIndex() {
        Pagination pagination = new Pagination(45, 10, 2);
        Assertions.assertEquals(10, pagination.getBeginIndex());
    }

    @Test
    public void testGetEndIndex() {
        Pagination pagination = new Pagination(45, 10, 2);
        Assertions.assertEquals(19, pagination.getEndIndex());
    }

    @Test
    public void testGetAmountOfPages() {
        Pagination pagination = new Pagination(45, 10, 2);
        Assertions.assertEquals(5, pagination.getAmountOfPages());
    }
}
