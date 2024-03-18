package in.er.annamalai.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.er.annamalai.userservice.entiry.UserEntity;
import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;
import in.er.annamalai.userservice.repository.UserRepository;
import in.er.annamalai.userservice.service.UserService;
import in.er.annamalai.userservice.util.PasswordHashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user, Long applicationId) {
        return new User(userRepository.save(new UserEntity(user, applicationId)));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id, Long applicationId) {
        return userRepository.findByIdAndApplicationId(id, applicationId).map(user -> {
            return new User(user);
        }).orElse(null);
    }

    @Override
    @Transactional
    public User updateUser(UpdateUserRequest user, long id, Long applicationId) {
        User existingUser = this.getUser(id, applicationId);
        String existingPasswordToRestore = null;
        if(user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            if(user.getOldPassword() == null || existingUser == null || !PasswordHashUtil.checkPassword(user.getOldPassword(), existingUser.getPassword())) {
                return null;
            }
        } else {
            existingPasswordToRestore = existingUser.getPassword();
        }
        return new User(userRepository.save(new UserEntity(user, id, existingPasswordToRestore, applicationId)));
    }

    @Override
    @Transactional
    public boolean deleteUser(long id, Long applicationId) {
        return userRepository.findByIdAndApplicationId(id, applicationId).map(user -> {
            if(user != null) {
                userRepository.delete(user);
                return true;
            }
            return false;
        }).orElse(false);
    }
    
}
