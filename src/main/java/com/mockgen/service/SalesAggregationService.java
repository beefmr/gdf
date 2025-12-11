package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesAggregationService {
    private final SalesDao salesDao;

    public SalesAggregationService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<RegionSummary> aggregateSalesByRegion() throws DataAccessException {
        List<Transaction> transactions = salesDao.getAllTransactions();

        Map<String, BigDecimal> regionTotals = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getRegion,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ));

        return regionTotals.entrySet().stream()
                .map(entry -> new RegionSummary(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}