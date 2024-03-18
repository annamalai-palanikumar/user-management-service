package in.er.annamalai.userservice.entiry;

import in.er.annamalai.userservice.entiry.rest.Application;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "application")
@Table(name="application")
@Getter
@Setter
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long divisionId;

    @ManyToOne
    @JoinColumn(name="client_id")
    private ClientEntity client;

    private boolean authorised;

    public ApplicationEntity() {
        super();
    }

    public ApplicationEntity(Long id) {
        this.id = id;
    }

    public ApplicationEntity(Application application, Long divisionId) {
        this.name = application.getName();
        this.description = application.getDescription();
        this.divisionId = divisionId;
        if(application.getClientId() != null) {
            this.client = new ClientEntity(application.getClientId());
        }
        this.authorised = application.isAuthorised();
    }

    public ApplicationEntity(Application application, Long id, Long divisionId) {
        this(application, divisionId);
        this.id = id;
    }
}
