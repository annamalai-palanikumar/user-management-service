package in.er.annamalai.userservice.entiry.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest extends User {

    private String oldPassword;

    public UpdateUserRequest() {
        super();
    }

    public UpdateUserRequest(String email, String password, String userName, String name, String oldPassword) {
        super(email, password, userName, name);
        this.oldPassword = oldPassword;
    }
}
