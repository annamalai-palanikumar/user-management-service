package in.er.annamalai.userservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.er.annamalai.userservice.entiry.rest.Client;
import in.er.annamalai.userservice.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable long id) {
        Client client = clientService.getClient(id);
        if(client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public Client updateClient(@RequestBody Client client, @PathVariable long id) {
        return clientService.updateClient(client, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable long id) {
        if(clientService.deleteClient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
}
