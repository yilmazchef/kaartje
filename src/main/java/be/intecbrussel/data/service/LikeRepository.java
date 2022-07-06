package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface LikeRepository extends JpaRepository < Like, UUID >, JpaSpecificationExecutor < Like > {

    @Query ( "select l from Like l where l.ticket.id = :ticketId" )
    Page < Like > findByTicket ( @Param ( "ticketId" ) @NotNull final UUID ticketId, @NotNull final Pageable pageable );

    @Query ( "select count(l) from Like l where l.ticket.id = :ticketId" )
    Integer countByTicket ( @Param ( "ticketId" ) @NotNull final UUID ticketId );

}