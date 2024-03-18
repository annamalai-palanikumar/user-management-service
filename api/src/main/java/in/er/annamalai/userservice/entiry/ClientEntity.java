package in.er.annamalai.userservice.entiry;

import in.er.annamalai.userservice.entiry.rest.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "client")
@Table(name="client")
@Getter
@Setter
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public ClientEntity() {
        super();
    }

    public ClientEntity(Long id) {
        this.id = id;
    }

    public ClientEntity(Client client) {
        this.name = client.getName();
        this.description = client.getDescription();
    }

    public ClientEntity(Client client, Long id) {
        this(client);
        this.id = id;
    }
}
