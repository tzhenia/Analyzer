package com.tzhenia.analyzer.service;

import com.tzhenia.analyzer.model.Record;
import com.tzhenia.analyzer.model.TradeStatistics;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileAnalysisServiceImplTest {

    @Test
    public void parseRecords() {
        List<String> rows = new ArrayList<>();
        rows.add("INFO\t50\t8660");
        rows.add("GOGL\t328\t107798");

        FileAnalysisServiceImpl fileAnalysisService = new FileAnalysisServiceImpl();
        List<Record> parsedRecord = fileAnalysisService.parseRecords(rows);

        assertEquals("INFO", parsedRecord.get(0).getStockSymbol());
        assertEquals(new BigDecimal(50), parsedRecord.get(0).getPrice());
        assertEquals(new BigDecimal(8660), parsedRecord.get(0).getVolume());

        assertEquals("GOGL", parsedRecord.get(1).getStockSymbol());
        assertEquals(new BigDecimal(328), parsedRecord.get(1).getPrice());
        assertEquals(new BigDecimal(107798), parsedRecord.get(1).getVolume());
    }

    @Test
    public void groupRecordsByStockSymbol() {
        List<Record> records = new ArrayList<>();

        records.add(new Record("GOGL", new BigDecimal(361), new BigDecimal(107873)));
        records.add(new Record("BAML", new BigDecimal(30), new BigDecimal(871)));
        records.add(new Record("GOGL", new BigDecimal(328), new BigDecimal(107798)));

        FileAnalysisServiceImpl fileAnalysisService = new FileAnalysisServiceImpl();
        Map<String, List<Record>> groupRecordsByStockSymbol = fileAnalysisService.getGroupRecordsByStockSymbol(records);

        assertEquals("GOGL", groupRecordsByStockSymbol.get("GOGL").get(0).getStockSymbol());
        assertEquals(new BigDecimal(361), groupRecordsByStockSymbol.get("GOGL").get(0).getPrice());
        assertEquals(new BigDecimal(107873), groupRecordsByStockSymbol.get("GOGL").get(0).getVolume());

        assertEquals("GOGL", groupRecordsByStockSymbol.get("GOGL").get(1).getStockSymbol());
        assertEquals(new BigDecimal(328), groupRecordsByStockSymbol.get("GOGL").get(1).getPrice());
        assertEquals(new BigDecimal(107798), groupRecordsByStockSymbol.get("GOGL").get(1).getVolume());

        assertEquals("BAML", groupRecordsByStockSymbol.get("BAML").get(0).getStockSymbol());
        assertEquals(new BigDecimal(30), groupRecordsByStockSymbol.get("BAML").get(0).getPrice());
        assertEquals(new BigDecimal(871), groupRecordsByStockSymbol.get("BAML").get(0).getVolume());
    }

    @Test
    public void generateTradeStatistics() {
        Map<String, List<Record>> map = new HashMap<>();

        List<Record> recordsGOGL = new ArrayList<>();
        recordsGOGL.add(new Record("GOGL", new BigDecimal(361), new BigDecimal(107873)));
        recordsGOGL.add(new Record("GOGL", new BigDecimal(328), new BigDecimal(107798)));
        map.put("GOGL", recordsGOGL);

        FileAnalysisServiceImpl fileAnalysisService = new FileAnalysisServiceImpl();
        List<TradeStatistics> tradeStatisticsList = fileAnalysisService.generateTradeStatistics(map);

        assertEquals("GOGL", tradeStatisticsList.get(0).getStockSymbol());
        assertEquals(new BigDecimal(361), tradeStatisticsList.get(0).getOpeningPrice());
        assertEquals(new BigDecimal(328), tradeStatisticsList.get(0).getClosingPrice());
        assertEquals(new BigDecimal(328), tradeStatisticsList.get(0).getMinimumPrice());
        assertEquals(new BigDecimal(361), tradeStatisticsList.get(0).getMaximumPrice());
        assertEquals(new BigDecimal(344.5), tradeStatisticsList.get(0).getAveragePrice());
        assertEquals(new BigDecimal(215671), tradeStatisticsList.get(0).getVolume());
    }
}