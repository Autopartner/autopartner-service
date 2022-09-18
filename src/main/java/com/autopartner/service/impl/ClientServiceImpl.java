package com.autopartner.service.impl;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.domain.Client;
import com.autopartner.repository.ClientRepository;
import com.autopartner.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {

  ClientRepository clientRepository;

  @Override
  public List<Client> findAll() {
    return clientRepository.findByActiveTrue();
  }

  @Override
  public Optional<Client> findById(Long id) {
    return clientRepository.findByIdAndActiveTrue(id);
  }

  private Client save(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public Client update(Client client, ClientRequest request) {
    client.update(request);
    return save(client);
  }

  @Override
  public void delete(Client client) {
    client.delete();
    save(client);
  }

  @Override
  public Client create(ClientRequest request, Long companyId) {
    return save(Client.create(request, companyId));
  }

  @Override
  public Long existsByPhone(String phone) {
    Optional<Client> client = clientRepository.findByPhoneAndActiveTrue(phone);
    if(client.isEmpty()){
      return null;
    }
    return client.get().getId();
  }

}
