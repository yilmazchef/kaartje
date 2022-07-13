package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.dto.TicketDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface IHomeApi {

    List < TicketDto > getTickets ( @NotNull final int page, @NotNull final int size );

    List < LikeDto > getLikes ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId );

    long getLikesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId );

    List < ShareDto > getShares ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    long getSharesCount ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    List < CommentDto > getComments ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    long getCommentsCount ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId );

    CommentDto postComment ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId, @NotNull CommentDto commentDto );

    CommentDto updateComment ( @NotNull @PathVariable ( "ticket_id" ) UUID ticketId,
                               @NotNull @PathVariable ( "comment_id" ) UUID commentId,
                               @NotNull CommentDto commentDto );

    LikeDto likeTicket ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId, @NotNull @Valid LikeDto likeDto );

    LikeDto likeTicket ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId );

    LikeDto deleteLike ( @PathVariable ( "ticket_id" ) @NotNull UUID ticketId,
                         @PathVariable ( "like_id" ) @NotNull UUID likeId );
}
