package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.RegionSummary;
import com.mockgen.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for aggregating sales data by various criteria.
 * Provides business logic for calculating regional sales totals.
 */
public class SalesAggregationService {
    private final SalesDao salesDao;

    /**
     * Constructs a SalesAggregationService with the specified data access object.
     *
     * @param salesDao the data access object for retrieving sales transactions
     */
    public SalesAggregationService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    /**
     * Aggregates sales data by region, calculating total sales amount for each region.
     * This method groups transactions by their region and sums up the amounts
     * for transactions in the same region.
     *
     * @return a list of RegionSummary objects containing region names and their total sales
     * @throws DataAccessException if there is an error accessing the sales data
     *
     * @see RegionSummary
     * @see Transaction
     */
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