package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.Transaction;
import java.util.List;

/**
 * Service for sorting and ranking sales transactions.
 * Provides business logic for retrieving top-performing sales transactions.
 */
public class SalesSortingService {
    private final SalesDao salesDao;

    /**
     * Constructs a SalesSortingService with the specified data access object.
     *
     * @param salesDao the data access object for retrieving sales transactions
     */
    public SalesSortingService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    /**
     * Retrieves the top N sales transactions ordered by amount in descending order.
     * Transactions with the highest amounts are returned first.
     * If there are fewer transactions than the requested count, all available
     * transactions are returned in sorted order.
     *
     * @param count the maximum number of top transactions to return
     * @return a list of top N transactions sorted by amount (highest first)
     * @throws DataAccessException if there is an error accessing the sales data
     * @throws IllegalArgumentException if count is negative
     *
     * @see Transaction
     */
    public List<Transaction> getTopSales(int count) throws DataAccessException {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative: " + count);
        }
        return salesDao.getTopTransactionsByAmount(count);
    }
}