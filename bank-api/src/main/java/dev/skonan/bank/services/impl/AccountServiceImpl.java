package dev.skonan.bank.services.impl;

import dev.skonan.bank.dtos.AccountDto;
import dev.skonan.bank.exceptions.OperationNonPermittedException;
import dev.skonan.bank.models.Account;
import dev.skonan.bank.repositories.AccountRepository;
import dev.skonan.bank.services.AccountService;
import dev.skonan.bank.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public Integer save(AccountDto dto) {
        // block account update -> iban cannot be changed (1)
        /*if (dto.getId() != null) {

            throw new OperationNonPermittedException(
                    "Account cannot be updated",
                    "save account",
                    "Account",
                    "Update not permitted"
            );
        }*/

        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);
        boolean userHasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();

        if (userHasAlreadyAnAccount && account.getUser().isActive()) {
            throw new OperationNonPermittedException(
                    "the selected user has already an active account",
                    "Create account",
                    "Account service",
                    "Account creation"
            );
        }

        // generate random IBAN when creating new account else do not update the IBAN (2)
        if (dto.getId() == null) {
            account.setIban(generateRandomIban());
        }

        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No account was found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private String generateRandomIban() {
        // generate an iban
        String iban = Iban.random(CountryCode.CI).toFormattedString();

        // check if the iban already exists
        boolean ibanExists = repository.findByIban(iban).isPresent();

        // if exists -> generate new random iban
        if (ibanExists) {
            generateRandomIban();
        }

        // if not exist -> return generated iban
        return iban;
    }
}
