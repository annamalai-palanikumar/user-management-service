package in.er.annamalai.userservice.service;

import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;

public interface UserService {
    User createUser(User user, Long applicationId);
    User getUser(long id, Long applicationId);
    User updateUser(UpdateUserRequest user, long id, Long applicationId);
    boolean deleteUser(long id, Long applicationId);
}
