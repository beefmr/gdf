package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for filtering sales transactions based on various criteria.
 * Provides business logic for querying transactions by product, region, and amount.
 */
public class SalesFilterService {
    private final SalesDao salesDao;

    /**
     * Constructs a SalesFilterService with the specified data access object.
     *
     * @param salesDao the data access object for retrieving sales transactions
     */
    public SalesFilterService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    /**
     * Filters transactions by product ID.
     * Returns all transactions that match the specified product identifier.
     *
     * @param productId the product ID to filter by (case-sensitive)
     * @return a list of transactions for the specified product
     * @throws DataAccessException if there is an error accessing the sales data
     *
     * @see Transaction
     */
    public List<Transaction> filterByProduct(String productId) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> productId.equals(t.getProductId()))
                .collect(Collectors.toList());
    }

    /**
     * Filters transactions by region.
     * Returns all transactions that match the specified region.
     *
     * @param region the region to filter by (case-sensitive)
     * @return a list of transactions for the specified region
     * @throws DataAccessException if there is an error accessing the sales data
     *
     * @see Transaction
     */
    public List<Transaction> filterByRegion(String region) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> region.equals(t.getRegion()))
                .collect(Collectors.toList());
    }

    /**
     * Filters transactions by minimum amount.
     * Returns all transactions with amount greater than the specified minimum.
     *
     * @param minAmount the minimum amount threshold (exclusive)
     * @return a list of transactions with amount greater than minAmount
     * @throws DataAccessException if there is an error accessing the sales data
     *
     * @see Transaction
     */
    public List<Transaction> filterByAmountGreaterThan(double minAmount) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> t.getAmount().doubleValue() > minAmount)
                .collect(Collectors.toList());
    }
}