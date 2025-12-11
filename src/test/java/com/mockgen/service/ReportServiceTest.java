package com.mockgen.service;

import com.mockgen.model.AppProperties;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Test
    void testReportServiceCreation() {
        AppProperties props = new AppProperties();
        ReportService service = new ReportService(props);
        assertNotNull(service);
    }

    @Test
    void testGenerateReportEmpty() throws Exception {
        AppProperties props = new AppProperties();
        props.setReportOutput("STDOUT");
        ReportService service = new ReportService(props);

        service.generateReport(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
}