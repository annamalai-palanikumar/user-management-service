package in.er.annamalai.userservice.entiry.rest;

import java.io.Serializable;

import in.er.annamalai.userservice.entiry.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
    private Long id;

    private String email;

    private String password;

    private String userName;

    private String name;

    public User() {
        super();
    }

    public User(UserEntity user) {
        if(user != null) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.userName = user.getUserName();
            this.name = user.getName();
        }
    }

    public User(String email, String password, String userName, String name) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.name = name;
    }
}
