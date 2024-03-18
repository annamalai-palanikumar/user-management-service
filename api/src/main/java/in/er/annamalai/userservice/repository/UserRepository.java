package in.er.annamalai.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.er.annamalai.userservice.entiry.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndApplicationId(Long id, Long applicationId);
}
