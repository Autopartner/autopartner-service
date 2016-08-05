package autopartner.service.impl;

import autopartner.domain.entity.Client;
import autopartner.repository.ClientRepository;
import autopartner.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by User on 8/2/2016.
 */
@Repository
@Transactional
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Iterable<Client> listAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Integer id) {
        return clientRepository.findOne(id);
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = getClientById(id);
        if (client != null) {
            client.setActive(false);
            saveClient(client);
        }
    }
}
