package com.autopartner.service;

import com.autopartner.domain.Client;

public interface ClientService {

  Iterable<Client> getByActiveTrue();

  Client getClientById(Long id);

  Client saveClient(Client client);

  void deleteClient(Long id);

}
