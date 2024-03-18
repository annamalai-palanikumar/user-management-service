package in.er.annamalai.userservice.service;

import in.er.annamalai.userservice.entiry.rest.Application;

public interface ApplicationService {
    Application createApplication(Application application, Long divisionId);
    Application getApplication(long id, Long divisionId);
    Application updateApplication(Application application, long id, Long divisionId);
    boolean deleteApplication(long id, Long divisionId);
}
