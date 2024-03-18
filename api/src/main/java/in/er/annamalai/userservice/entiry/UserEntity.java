package in.er.annamalai.userservice.entiry;

import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;
import in.er.annamalai.userservice.util.PasswordHashUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user")
@Table(name="user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String userName;

    private String name;

    @ManyToOne
    @JoinColumn(name="application_id", nullable=false)
    private ApplicationEntity application;


    public UserEntity() {
        super();
    }

    public UserEntity(User user, Long applicationId) {
        this.email = user.getEmail();
        if(user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            this.password = PasswordHashUtil.hashPassword(user.getPassword());
        }
        this.userName = user.getUserName();
        this.name = user.getName();
        this.application = new ApplicationEntity(applicationId);
    }

    public UserEntity(UpdateUserRequest user, long id, String existingPassword, Long applicationId) {
        this(user, applicationId);
        this.id = id;
        if(existingPassword != null) {
            this.password = existingPassword;
        }
    }
}
