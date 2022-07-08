package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface ShareRepository extends JpaRepository < Share, UUID >, JpaSpecificationExecutor < Share > {

    @Query ( "select s from Share s where s.ticket.id = :ticketId" )
    Page < Share > findAllByTicket ( @Param ( "ticketId" ) @NotNull UUID ticketId, Pageable pageable );

    @Query ( "select s from Share s where s.ticket.id = :ticketId" )
    List < Share > findAllByTicket ( @Param ( "ticketId" ) @NotNull UUID ticketId );

    @Query ( "select count(s) from Share s where s.ticket.id = :ticket_id" )
    int countByTicket ( @Param ( "ticket_id" ) @NotNull final UUID ticketId );
}