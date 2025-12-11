package com.mockgen.service;

import com.mockgen.model.AppProperties;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReportService {
    private final AppProperties properties;

    public ReportService(AppProperties properties) {
        this.properties = properties;
    }

    public void generateReport(List<RegionSummary> regionSummaries,
                               List<Transaction> topSales,
                               List<Transaction> filteredSales) throws IOException {

        StringBuilder report = new StringBuilder();

        // Sales by Region
        report.append("--- Sales by Region ---\n");
        for (RegionSummary summary : regionSummaries) {
            report.append(summary).append("\n");
        }
        report.append("\n");

        // Top Sales
        report.append("--- Top ").append(properties.getTopSalesCount())
                .append(" Sales (ID) ---\n");
        for (Transaction transaction : topSales) {
            report.append(transaction.getTransactionId()).append("\n");
        }
        report.append("\n");

        // Filtered Sales
        report.append("--- Filtered by Product ").append(properties.getFilterProductId())
                .append(" ---\n");
        for (Transaction transaction : filteredSales) {
            report.append(transaction).append("\n");
        }

        // Output
        if ("STDOUT".equalsIgnoreCase(properties.getReportOutput())) {
            System.out.println(report.toString());
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter("sales-report.txt"))) {
                writer.print(report.toString());
            }
        }
    }
}