package com.mockgen.service;

import com.mockgen.dao.SalesDao;
import com.mockgen.dao.DataAccessException;
import com.mockgen.model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class SalesFilterService {
    private final SalesDao salesDao;

    public SalesFilterService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<Transaction> filterByProduct(String productId) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> productId.equals(t.getProductId()))
                .collect(Collectors.toList());
    }

    public List<Transaction> filterByRegion(String region) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> region.equals(t.getRegion()))
                .collect(Collectors.toList());
    }

    public List<Transaction> filterByAmountGreaterThan(double minAmount) throws DataAccessException {
        return salesDao.getAllTransactions().stream()
                .filter(t -> t.getAmount().doubleValue() > minAmount)
                .collect(Collectors.toList());
    }
}