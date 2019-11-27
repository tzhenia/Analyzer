package com.tzhenia.analyzer.service;

import com.tzhenia.analyzer.model.Record;
import com.tzhenia.analyzer.model.TradeStatistics;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAnalysisServiceImpl implements FileAnalysisService {
    private static final String SEPARATOR = "\t";
    private static final String ROOT = "src/main/resources/static/";
    private static final String FILE_WITH_DATA = ROOT + "/stocks.txt";
    private static final String FILE_FOR_SAVE_RESULT = ROOT + "results.txt";
    private static final Logger logger = Logger.getLogger(FileAnalysisServiceImpl.class.getName());

    @Override
    public void dataAnalysis() {
        logger.info("Starting analyze!");

        try (Stream<String> stream = Files.lines(Paths.get(FILE_WITH_DATA))) {
            List<String> rows = stream
                    .collect(Collectors.toList());
            logger.info("Load file: " + FILE_WITH_DATA + " successfully!");

            List<Record> records = parseRecords(rows);
            Map<String, List<Record>> groupRecordsByStockSymbol = getGroupRecordsByStockSymbol(records);
            List<TradeStatistics> tradeStatisticsList = generateTradeStatistics(groupRecordsByStockSymbol);
            saveResultToFile(tradeStatisticsList);

        } catch (IOException e) {
            logger.info("Load file: " + FILE_WITH_DATA + " unsuccessfully!");
            logger.error(e);
        }
    }

    @Override
    public List<Record> parseRecords(List<String> rows) {
        List<Record> records = new ArrayList<>();
        final String REGEX = "[^a-zA-Z0-9]";

        rows.forEach(
                (line) -> {
                    String[] lineValues = line.split(SEPARATOR);

                    records.add(
                            new Record(
                                    lineValues[0].replaceAll(REGEX, ""),
                                    new BigDecimal(lineValues[1]),
                                    new BigDecimal(lineValues[2])
                            )
                    );
                }
        );

        logger.info("Parse records");
        return records;
    }

    @Override
    public Map<String, List<Record>> getGroupRecordsByStockSymbol(List<Record> records) {
        Map<String, List<Record>> map = records.stream()
                .collect(Collectors.groupingBy(Record::getStockSymbol));

        logger.info("Group records by stock symbol");
        return map;
    }

    @Override
    public List<TradeStatistics> generateTradeStatistics(Map<String, List<Record>> map) {
        List<TradeStatistics> tradeStatisticsList = new ArrayList<>();

        map.forEach(
                (stockSymbol, record) -> {

                    DoubleSummaryStatistics statistics = record.stream()
                            .mapToDouble(i -> i.getPrice().doubleValue())
                            .summaryStatistics();

                    BigDecimal openingPrice = record.get(0).getPrice();
                    BigDecimal closingPrice = record.get(record.size() - 1).getPrice();
                    BigDecimal minimumPrice = new BigDecimal(statistics.getMin());
                    BigDecimal maximumPrice = new BigDecimal(statistics.getMax());
                    BigDecimal averagePrice = new BigDecimal(statistics.getAverage());
                    BigDecimal volume = record.stream().map(Record::getVolume).reduce(BigDecimal.ZERO, BigDecimal::add);

                    tradeStatisticsList.add(
                            new TradeStatistics(
                                    stockSymbol,
                                    openingPrice,
                                    closingPrice,
                                    minimumPrice,
                                    maximumPrice,
                                    averagePrice.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros(),
                                    volume
                            )
                    );
                }
        );

        logger.info("Generate trade statistics");
        return tradeStatisticsList;
    }

    private void saveResultToFile(List<TradeStatistics> tradeStatisticsList) {
        StringBuilder header = getHeaderForFile();
        StringBuilder data = getLinesFromTradeStatistics(tradeStatisticsList);

        try {
            Files.write(Paths.get(FILE_FOR_SAVE_RESULT), header.toString().getBytes());
            Files.write(Paths.get(FILE_FOR_SAVE_RESULT), data.toString().getBytes(), StandardOpenOption.APPEND);
            logger.info("Result save to file: " + FILE_FOR_SAVE_RESULT + " successfully!");

        } catch (IOException e) {
            logger.info("Result save to file: " + FILE_FOR_SAVE_RESULT + " unsuccessfully!");
            logger.error(e);
        }
    }

    private StringBuilder getHeaderForFile() {
        logger.info("Get header for file");
        return new StringBuilder()
                .append("Stock")
                .append(SEPARATOR)
                .append("Open")
                .append(SEPARATOR)
                .append("Close")
                .append(SEPARATOR)
                .append("Min")
                .append(SEPARATOR)
                .append(SEPARATOR)
                .append("Max")
                .append(SEPARATOR)
                .append(SEPARATOR)
                .append("Average")
                .append(SEPARATOR)
                .append(SEPARATOR)
                .append(SEPARATOR)
                .append("Vol \n");
    }

    private StringBuilder getLinesFromTradeStatistics(List<TradeStatistics> tradeStatisticsList) {
        StringBuilder data = new StringBuilder();

        for (TradeStatistics item : tradeStatisticsList) {
            data.append(item.getStockSymbol());
            data.append(SEPARATOR);
            data.append(item.getOpeningPrice());
            data.append(SEPARATOR);
            data.append(SEPARATOR);
            data.append(item.getClosingPrice());
            data.append(SEPARATOR);
            data.append(SEPARATOR);
            data.append(item.getMinimumPrice());
            data.append(SEPARATOR);
            data.append(SEPARATOR);
            data.append(item.getMaximumPrice());
            data.append(SEPARATOR);
            data.append(SEPARATOR);
            data.append(item.getAveragePrice());
            data.append(SEPARATOR);
            data.append(SEPARATOR);
            data.append(item.getVolume());
            data.append("\n");
        }

        logger.info("Get lines from trade statistics");
        return data;
    }
}