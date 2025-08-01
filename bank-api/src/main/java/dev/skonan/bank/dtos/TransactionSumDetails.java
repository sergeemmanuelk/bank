package dev.skonan.bank.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionSumDetails {
    LocalDate getTransactionDate();

    BigDecimal getAmount();
}
