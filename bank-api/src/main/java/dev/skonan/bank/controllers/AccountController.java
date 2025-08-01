package dev.skonan.bank.controllers;

import dev.skonan.bank.dtos.AccountDto;
import dev.skonan.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody AccountDto accountDto
    ) {
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<AccountDto> findById(
            @PathVariable("account-id") Integer accountId
    ) {
        return ResponseEntity.ok(accountService.findById(accountId));
    }

    @DeleteMapping("/{account-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("account-id") Integer accountId
    ) {
        accountService.delete(accountId);
        return ResponseEntity.accepted().build();
    }
}
