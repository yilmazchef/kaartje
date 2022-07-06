package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>, JpaSpecificationExecutor<Comment> {

    @Query("select c from Comment c where c.ticket.id = :ticketId")
    Page<Comment> findAllByTicket(@Param("ticketId") @NotNull UUID ticketId, Pageable pageable);

    @Query("select c from Comment c where c.ticket.id = :ticketId")
    List<Comment> findAllByTicket(@Param("ticketId") @NotNull UUID ticketId);

    @Query("select c from Comment c where c.isDeleted = true")
    List<Comment> findAllActive();

    @Query("select c from Comment c where c.isDeleted = false")
    List<Comment> findAllDeleted();

    @Query ( "select count(l) from Comment c where c.ticket.id = :ticketId" )
    Integer countByTicket ( @Param ( "ticketId" ) @NotNull final UUID ticketId );



}