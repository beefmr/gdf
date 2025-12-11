package com.mockgen.service;

import com.mockgen.model.AppProperties;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Service for generating formatted sales reports.
 * Provides business logic for creating comprehensive sales summaries
 * with multiple sections including regional totals, top sales, and filtered results.
 */
public class ReportService {
    private final AppProperties properties;

    /**
     * Constructs a ReportService with the specified application properties.
     *
     * @param properties the application configuration properties
     */
    public ReportService(AppProperties properties) {
        this.properties = properties;
    }

    /**
     * Generates a comprehensive sales report containing three main sections:
     * 1. Sales aggregated by region with total amounts
     * 2. Top N sales transactions (by ID only)
     * 3. Sales filtered by configured product ID
     *
     * The report output destination is determined by the configuration:
     * - "STDOUT": Outputs to standard output
     * - Any other value: Writes to "sales-report.txt" file
     *
     * @param regionSummaries list of regional sales summaries
     * @param topSales list of top sales transactions
     * @param filteredSales list of sales filtered by product
     * @throws IOException if there is an error writing the report to file
     *
     * @see RegionSummary
     * @see Transaction
     * @see AppProperties
     */
    public void generateReport(List<RegionSummary> regionSummaries,
                               List<Transaction> topSales,
                               List<Transaction> filteredSales) throws IOException {

        StringBuilder report = new StringBuilder();

        // Sales by Region section
        report.append("--- Sales by Region ---\n");
        for (RegionSummary summary : regionSummaries) {
            report.append(summary).append("\n");
        }
        report.append("\n");

        // Top Sales section
        report.append("--- Top ").append(properties.getTopSalesCount())
                .append(" Sales (ID) ---\n");
        for (Transaction transaction : topSales) {
            report.append(transaction.getTransactionId()).append("\n");
        }
        report.append("\n");

        // Filtered Sales section
        report.append("--- Filtered by Product ").append(properties.getFilterProductId())
                .append(" ---\n");
        for (Transaction transaction : filteredSales) {
            report.append(transaction).append("\n");
        }

        // Determine output destination based on configuration
        if ("STDOUT".equalsIgnoreCase(properties.getReportOutput())) {
            System.out.println(report.toString());
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter("sales-report.txt"))) {
                writer.print(report.toString());
            }
        }
    }
}