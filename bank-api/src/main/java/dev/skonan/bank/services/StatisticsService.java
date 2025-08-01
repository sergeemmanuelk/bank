package dev.skonan.bank.services;

import dev.skonan.bank.dtos.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {
    List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate endDate, Integer userId);

    BigDecimal getAccountBalance(Integer userId);

    BigDecimal highestTransfer(Integer userId);

    BigDecimal highestDeposit(Integer userId);
}
