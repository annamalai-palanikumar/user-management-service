package in.er.annamalai.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.er.annamalai.userservice.entiry.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
