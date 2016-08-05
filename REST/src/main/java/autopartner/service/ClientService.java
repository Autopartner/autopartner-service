package autopartner.service;

import autopartner.domain.entity.Client;

public interface ClientService {

    Iterable<Client> listAllClients();

    Client getClientById(Integer id);

    Client saveClient(Client client);

    void deleteClient(Integer id);

}
