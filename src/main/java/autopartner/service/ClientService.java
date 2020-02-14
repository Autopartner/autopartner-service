package autopartner.service;

import autopartner.domain.entity.Client;

public interface ClientService {

    Iterable<Client> getByActiveTrue();

    Client getClientById(Long id);

    Client saveClient(Client client);

    void deleteClient(Long id);

}
