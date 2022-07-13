package be.intecbrussel.views.ticket;

import org.springframework.web.bind.annotation.PathVariable;

import be.intecbrussel.data.entity.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface ITicketApi {

    List < Ticket > getTickets ( @NotNull final int page, @NotNull final int size );

    List < Like > getLikes ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId );

    long getLikesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId );

    List < Share > getShares ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    long getSharesCount ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    List < Comment > getComments ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    long getCommentsCount ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    Comment postComment ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId, @NotNull Comment comment );

    Comment updateComment ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId,
                               @NotNull @PathVariable ( "comment_id" ) UUID commentId,
                               @NotNull Comment comment );

    Like likeTicket ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId, @NotNull @Valid Like like );

    Like likeTicket ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId );

    Like deleteLike ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId,
                         @PathVariable ( "like_id" ) @NotNull UUID likeId );
}
