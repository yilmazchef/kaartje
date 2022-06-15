package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Response;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResponseRepository extends JpaRepository<Response, UUID>, JpaSpecificationExecutor<Response> {

}