package in.er.annamalai.userservice.service;

import in.er.annamalai.userservice.entiry.rest.Client;

public interface ClientService {
    Client createClient(Client Client);
    Client getClient(long id);
    Client updateClient(Client Client, long id);
    boolean deleteClient(long id);
}
