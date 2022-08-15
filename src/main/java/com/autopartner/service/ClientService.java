package com.autopartner.service;

import com.autopartner.api.dto.ClientRequest;
import com.autopartner.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();

    Optional<Client> findById(Long id);

    void delete(Client client);

    Client create(ClientRequest request, Long companyId);

    Client update(Client client, ClientRequest request);

    boolean existsByPhone(String phone);
}
