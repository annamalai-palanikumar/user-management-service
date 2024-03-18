package in.er.annamalai.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.er.annamalai.userservice.entiry.ClientEntity;
import in.er.annamalai.userservice.entiry.rest.Client;
import in.er.annamalai.userservice.repository.ClientRepository;
import in.er.annamalai.userservice.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Client createClient(Client client) {
        return new Client(clientRepository.save(new ClientEntity(client)));
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClient(long id) {
        return clientRepository.findById(id).map(client -> {
                return new Client(client);
        }).orElse(null);
    }

    @Override
    @Transactional
    public Client updateClient(Client client, long id) {
        Client existingClient = this.getClient(id);
        if(existingClient == null) {
            return null;
        }
        return new Client(clientRepository.save(new ClientEntity(client, id)));
    }

    @Override
    @Transactional
    public boolean deleteClient(long id) {
        return clientRepository.findById(id).map(client -> {
            if(client != null) {
                clientRepository.delete(client);
                return true;
            }
            return false;
        }).orElse(false);
    }
    
}
