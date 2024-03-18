package in.er.annamalai.userservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.er.annamalai.userservice.entiry.rest.UpdateUserRequest;
import in.er.annamalai.userservice.entiry.rest.User;
import in.er.annamalai.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user, @RequestHeader("x-application-id") Long applicationId) {
        return userService.createUser(user, applicationId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id, @RequestHeader("x-application-id") Long applicationId) {
        User user = userService.getUser(id, applicationId);
        if(user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody UpdateUserRequest user, @PathVariable long id, @RequestHeader("x-application-id") Long applicationId) {
        return userService.updateUser(user, id, applicationId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id, @RequestHeader("x-application-id") Long applicationId) {
        if(userService.deleteUser(id, applicationId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
}
