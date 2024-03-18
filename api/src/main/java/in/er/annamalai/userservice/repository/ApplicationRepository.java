package in.er.annamalai.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.er.annamalai.userservice.entiry.ApplicationEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    Optional<ApplicationEntity> findByIdAndDivisionId(Long id, Long divisionId);
}
