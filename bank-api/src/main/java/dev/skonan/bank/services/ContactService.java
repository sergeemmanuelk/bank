package dev.skonan.bank.services;

import dev.skonan.bank.dtos.ContactDto;

import java.util.List;

public interface ContactService extends AbstractService<ContactDto> {
    List<ContactDto> findAllByUserId(Integer userId);
}
