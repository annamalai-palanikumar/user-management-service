package in.er.annamalai.userservice.entiry.rest;

import java.io.Serializable;

import in.er.annamalai.userservice.entiry.ApplicationEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Application implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Long divisionId;

    private Long clientId;

    private boolean authorised;

    public Application() {
        super();
    }

    public Application(ApplicationEntity application) {
        if(application != null) {
            this.id = application.getId();
            this.name = application.getName();
            this.description = application.getDescription();
            this.divisionId = application.getDivisionId();
            if(application.getClient() != null) {
                this.clientId = application.getClient().getId();
            }
            this.authorised = application.isAuthorised();
        }
    }

    public Application(String name, String description, Long divisionId, Long clientId, boolean authorised) {
        this.name = name;
        this.description = description;
        this.divisionId = divisionId;
        this.clientId = clientId;
        this.authorised = authorised;
    }
}
