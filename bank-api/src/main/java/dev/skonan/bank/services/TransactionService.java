package dev.skonan.bank.services;

import dev.skonan.bank.dtos.TransactionDto;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto> {
    List<TransactionDto> findAllByUserId(Integer userId);
}
