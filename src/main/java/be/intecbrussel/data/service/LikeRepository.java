package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID>, JpaSpecificationExecutor<Like> {

    Page<Like> findAllByTicket_Id(@NotNull UUID ticketId, Pageable pageable);

}