package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface ShareRepository extends JpaRepository<Share, UUID>, JpaSpecificationExecutor<Share> {

    Page<Share> findAllByTicketId(@NotNull UUID ticketId, Pageable pageable);

    Page<Share> findAllByTicket_Id(@NotNull UUID ticketId, Pageable pageable);

}