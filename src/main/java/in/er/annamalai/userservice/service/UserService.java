package in.er.annamalai.userservice.service;

import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;

public interface UserService {
    User createUser(User user);
    User getUser(long id);
    User updateUser(UpdateUserRequest user, long id);
    boolean deleteUser(long id);
}
