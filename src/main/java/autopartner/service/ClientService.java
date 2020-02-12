package autopartner.service;

import autopartner.domain.entity.Client;

public interface ClientService {

    Iterable<Client> getByActiveTrue();

    Client getClientById(Integer id);

    Client saveClient(Client client);

    void deleteClient(Integer id);

}
