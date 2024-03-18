package in.er.annamalai.userservice.entiry.rest;

import java.io.Serializable;

import in.er.annamalai.userservice.entiry.ClientEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client implements Serializable {
    private Long id;

    private String name;

    private String description;

    public Client() {
        super();
    }

    public Client(ClientEntity client) {
        if(client != null) {
            this.id = client.getId();
            this.name = client.getName();
            this.description = client.getDescription();
        }
    }

    public Client(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
