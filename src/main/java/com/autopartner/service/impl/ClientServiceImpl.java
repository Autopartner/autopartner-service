package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Client;
import com.autopartner.repository.ClientRepository;
import com.autopartner.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 8/2/2016.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {

  ClientRepository clientRepository;

  @Override
  public Iterable<Client> getByActiveTrue() {
    return clientRepository.findByActiveTrue();
  }

  @Override
  public Client getClientById(Long id) {
    return clientRepository.findById(id).get();
  }

  @Override
  public Client saveClient(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public void deleteClient(Long id) {
    Client client = getClientById(id);
    if (client != null) {
      client.setActive(false);
      saveClient(client);
    }
  }
}
