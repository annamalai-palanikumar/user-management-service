package in.er.annamalai.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.er.annamalai.userservice.entiry.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    
}
