package in.er.annamalai.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.er.annamalai.userservice.entiry.ApplicationEntity;
import in.er.annamalai.userservice.entiry.rest.Application;
import in.er.annamalai.userservice.repository.ApplicationRepository;
import in.er.annamalai.userservice.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public Application createApplication(Application application, Long divisionId) {
        return new Application(applicationRepository.save(new ApplicationEntity(application, divisionId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Application getApplication(long id, Long divisionId) {
        return applicationRepository.findByIdAndDivisionId(id, divisionId).map(application -> {
                return new Application(application);
        }).orElse(null);
    }

    @Override
    @Transactional
    public Application updateApplication(Application application, long id, Long divisionId) {
        Application existingApplication = this.getApplication(id, divisionId);
        if(existingApplication == null) {
            return null;
        }
        return new Application(applicationRepository.save(new ApplicationEntity(application, id, existingApplication.getDivisionId())));
    }

    @Override
    @Transactional
    public boolean deleteApplication(long id, Long divisionId) {
        return applicationRepository.findByIdAndDivisionId(id, divisionId).map(application -> {
            if(application != null) {
                applicationRepository.delete(application);
                return true;
            }
            return false;
        }).orElse(false);
    }
    
}
