package com.tzhenia.analyzer.service;

import com.tzhenia.analyzer.model.Record;
import com.tzhenia.analyzer.model.TradeStatistics;

import java.util.List;
import java.util.Map;

public interface FileAnalysisService {
    void dataAnalysis();

    List<Record> parseRecords(List<String> rows);

    Map<String, List<Record>> getGroupRecordsByStockSymbol(List<Record> records);

    List<TradeStatistics> generateTradeStatistics(Map<String, List<Record>> map);
}