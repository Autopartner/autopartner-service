package com.autopartner.service;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
  List<Client> findAll(Long companyId);

  Optional<Client> findById(Long id, Long companyId);

  void delete(Client client);

  Client create(ClientRequest request, Long companyId);

  Client update(Client client, ClientRequest request);

  Optional<Long> findIdByPhone(String phone, Long companyId);
}
