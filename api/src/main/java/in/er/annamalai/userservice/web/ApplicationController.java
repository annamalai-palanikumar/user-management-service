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

import in.er.annamalai.userservice.entiry.rest.Application;
import in.er.annamalai.userservice.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public Application createApplication(@RequestBody Application application, @RequestHeader("x-division-id") Long divisionId) {
        return applicationService.createApplication(application, divisionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable long id, @RequestHeader("x-division-id") Long divisionId) {
        Application application = applicationService.getApplication(id, divisionId);
        if(application != null) {
            return ResponseEntity.ok(application);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public Application updateApplication(@RequestBody Application application, @PathVariable long id, @RequestHeader("x-division-id") Long divisionId) {
        return applicationService.updateApplication(application, id, divisionId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApplication(@PathVariable long id, @RequestHeader("x-division-id") Long divisionId) {
        if(applicationService.deleteApplication(id, divisionId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
}
