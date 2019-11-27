package com.tzhenia.analyzer;

import com.tzhenia.analyzer.service.FileAnalysisServiceImpl;

public class Starter {
    public static void main(String[] args) {
        FileAnalysisServiceImpl fileAnalysisService = new FileAnalysisServiceImpl();
        fileAnalysisService.dataAnalysis();
    }
}