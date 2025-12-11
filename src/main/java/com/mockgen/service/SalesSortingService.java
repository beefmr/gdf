package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.Transaction;
import java.util.List;

public class SalesSortingService {
    private final SalesDao salesDao;

    public SalesSortingService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<Transaction> getTopSales(int count) throws DataAccessException {
        return salesDao.getTopTransactionsByAmount(count);
    }
}