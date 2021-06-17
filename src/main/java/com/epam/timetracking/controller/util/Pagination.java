package com.epam.timetracking.controller.util;

import lombok.Data;

import java.io.Serializable;

/**
 * The Pagination class represents the holder for pagination info.
 */
@Data
public class Pagination implements Serializable {
    private static final long serialVersionUID = -4433516239422172642L;
    private int listSize;
    private int currentPage;
    private int beginIndex;
    private int endIndex;
    private int resultsPerPage;
    private int amountOfPages;

    public Pagination(int listSize, int resultsPerPage, int currentPage) {
        this.listSize = listSize;
        this.currentPage = currentPage;
        this.beginIndex = resultsPerPage * (currentPage - 1);
        this.endIndex = beginIndex + resultsPerPage - 1;
        this.resultsPerPage = resultsPerPage;
        this.amountOfPages = calculateAmountOfPages(listSize, resultsPerPage);
    }

    private int calculateAmountOfPages(int listSize, int resultsPerPage) {
        if (listSize % resultsPerPage == 0) {
            return listSize / resultsPerPage;
        }
        return (listSize / resultsPerPage) + 1;
    }
}
