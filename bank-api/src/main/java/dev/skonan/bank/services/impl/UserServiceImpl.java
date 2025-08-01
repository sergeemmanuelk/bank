package dev.skonan.bank.services.impl;

import dev.skonan.bank.dtos.AccountDto;
import dev.skonan.bank.dtos.UserDto;
import dev.skonan.bank.models.Account;
import dev.skonan.bank.models.User;
import dev.skonan.bank.repositories.UserRepository;
import dev.skonan.bank.services.AccountService;
import dev.skonan.bank.services.UserService;
import dev.skonan.bank.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectsValidator<UserDto> validator;

    private final AccountService accountService;

    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);

        return userRepository.save(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Integer validateAccount(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        if (user.getAccount() == null) {
            // create a bank account
            AccountDto account = AccountDto.builder()
                    .user(UserDto.fromEntity(user))
                    .build();
            var savedAccount = accountService.save(account);
            user.setAccount(
                    Account.builder()
                            .id(savedAccount)
                            .build()
            );
        }

        user.setActive(true);
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        user.setActive(false);
        userRepository.save(user);

        return user.getId();
    }
}
