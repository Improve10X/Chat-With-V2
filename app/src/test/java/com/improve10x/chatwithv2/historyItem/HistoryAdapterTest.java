package com.improve10x.chatwithv2.historyItem;

import junit.framework.TestCase;

import org.junit.Test;


public class HistoryAdapterTest extends TestCase {
    private HistoryAdapter historyAdapter;

    @Test
    public void given0_returnEmpty(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(-1);
        assertEquals("", result);

    }

    @Test
    public void givenNegativeValue_returnEmpty(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(-1);
        assertEquals("", result);
    }


    @Test
    public void givenCurrentTime_returnCurrentTime(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(System.currentTimeMillis());
        assertEquals(System.currentTimeMillis(), result);
    }

    @Test
    public void givenCurrentTimeMinus24Hours_returnYesterday(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(System.currentTimeMillis() - 86400000);
        assertEquals("Yesterday", result);
    }

    @Test
    public void givenCurrentTimeMinus48Hours_returnYesterday(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(System.currentTimeMillis() - 48 * 60 * 60 * 1000);
        assertEquals("01 Aug", result);
    }

    @Test
    public void givenCurrentTimeMinus96Hours_returnYesterday(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(System.currentTimeMillis() - 96 * 60 * 60 * 1000);
        assertEquals("30 Jul", result);
    }

    @Test
    public void givenLastYearTimeInMillis_returnDayMonthAndYear(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(978546600000l);
        assertEquals("04 Jan 2001", result);
    }

    @Test
    public void given864757800000_return19970528(){
        historyAdapter = new HistoryAdapter();
        String result = historyAdapter.getDisplayDate(864757800000l);
        assertEquals("28 May 1997", result);
    }
}