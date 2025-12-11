package com.mockgen.service;

import com.mockgen.model.AppProperties;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void testGenerateReportToStdOut() throws IOException {
        AppProperties properties = new AppProperties();
        properties.setReportOutput("STDOUT");
        properties.setTopSalesCount(2);
        properties.setFilterProductId("P-123");

        ReportService reportService = new ReportService(properties);

        List<RegionSummary> regionSummaries = Arrays.asList(
                new RegionSummary("North", new BigDecimal("450.00")),
                new RegionSummary("South", new BigDecimal("20.50"))
        );

        List<Transaction> topSales = Arrays.asList(
                new Transaction("1004", LocalDate.of(2025, 10, 12), "P-789", new BigDecimal("300.00"), "North"),
                new Transaction("1001", LocalDate.of(2025, 10, 10), "P-123", new BigDecimal("150.00"), "North")
        );

        List<Transaction> filteredSales = Arrays.asList(
                new Transaction("1001", LocalDate.of(2025, 10, 10), "P-123", new BigDecimal("150.00"), "North"),
                new Transaction("1003", LocalDate.of(2025, 10, 11), "P-123", new BigDecimal("150.00"), "West")
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            reportService.generateReport(regionSummaries, topSales, filteredSales);
            String output = outContent.toString();
            assertTrue(output.contains("--- Sales by Region ---"));
            assertTrue(output.contains("North: 450.00"));
            assertTrue(output.contains("South: 20.50"));
            assertTrue(output.contains("--- Top 2 Sales (ID) ---"));
            assertTrue(output.contains("1004"));
            assertTrue(output.contains("1001"));
            assertTrue(output.contains("--- Filtered by Product P-123 ---"));
            assertTrue(output.contains("1001,2025-10-10,P-123,150.00,North"));
            assertTrue(output.contains("1003,2025-10-11,P-123,150.00,West"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testGenerateReportToFile() throws IOException {
        File reportFile = new File(tempDir.toFile(), "sales-report.txt");

        AppProperties properties = new AppProperties();
        properties.setReportOutput(reportFile.getAbsolutePath());
        properties.setTopSalesCount(2);
        properties.setFilterProductId("P-123");

        ReportService reportService = new ReportService(properties);

        List<RegionSummary> regionSummaries = Arrays.asList(
                new RegionSummary("North", new BigDecimal("450.00"))
        );

        List<Transaction> topSales = Arrays.asList(
                new Transaction("1004", LocalDate.of(2025, 10, 12), "P-789", new BigDecimal("300.00"), "North")
        );

        List<Transaction> filteredSales = Arrays.asList(
                new Transaction("1001", LocalDate.of(2025, 10, 10), "P-123", new BigDecimal("150.00"), "North")
        );

        reportService.generateReport(regionSummaries, topSales, filteredSales);

        assertTrue(reportFile.exists());
        String content = Files.readString(reportFile.toPath());
        assertTrue(content.contains("--- Sales by Region ---"));
        assertTrue(content.contains("North: 450.00"));
        assertTrue(content.contains("--- Top 2 Sales (ID) ---"));
        assertTrue(content.contains("1004"));
        assertTrue(content.contains("--- Filtered by Product P-123 ---"));
        assertTrue(content.contains("1001,2025-10-10,P-123,150.00,North"));
    }
}