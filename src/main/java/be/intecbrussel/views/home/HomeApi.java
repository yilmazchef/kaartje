package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.dto.TicketDto;
import be.intecbrussel.data.service.*;
import be.intecbrussel.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping ( "/api/home" )
@RequiredArgsConstructor
public class HomeApi {

    private final TicketService ticketService;
    private final AuthenticatedUser authenticatedUser;
    private final UserService userService;
    private final LikeService likeService;
    private final ShareService shareService;
    private final CommentService commentService;

    @GetMapping ( "/tickets" )
    public Page < TicketDto > getTickets ( @NotNull final Pageable pageable ) {
        return ticketService.listDto ( pageable );
    }

    @GetMapping ( "/tickets/{ticket_id}/likes" )
    public Page < LikeDto > getLikes ( @NotNull final Pageable pageable, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.listByTicketIdDto ( ticketId, pageable );
    }

    @GetMapping ( "/tickets/{ticket_id}/likes/count" )
    public long getLikesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.countByTicketDto ( ticketId );
    }

    @GetMapping ( "/tickets/{ticket_id}/shares" )
    public Page < ShareDto > getShares ( @NotNull final Pageable pageable, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.listByTicketIdDto ( ticketId, pageable );
    }

    // TODO: add methods to related services.
    @GetMapping ( "/tickets/{ticket_id}/shares/count" )
    public long getSharesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.countByTicketDto ( ticketId );
    }

    @GetMapping ( "/tickets/{ticket_id}/comments" )
    public Page < CommentDto > getComments ( @NotNull final Pageable pageable, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.listByTicketIdDto ( ticketId, pageable );
    }

    @GetMapping ( "/tickets/{ticket_id}/comments/count" )
    public long getCommentsCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.countByTicketDto ( ticketId );
    }

    @PostMapping ( "/tickets/{ticket_id}/comments" )
    public CommentDto postComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId, @NotNull final CommentDto commentDto ) {

        if ( ticketService.get ( ticketId ).isPresent ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        if ( authenticatedUser.get ( ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authenticated"
            );
        }

        commentDto.getTicket ( ).setTicketId ( ticketId );
        commentDto.getCreatedBy ( ).setUserId ( authenticatedUser.get ( ).get ( ).getId ( ) );
        return commentService.createDto ( commentDto );
    }

    @PutMapping ( "/tickets/{ticket_id}/comments/{comment_id}" )
    public CommentDto updateComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId,
                                      @NotNull @PathVariable ( "comment_id" ) final UUID commentId,
                                      @NotNull final CommentDto commentDto ) {

        if ( ticketService.get ( ticketId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        if ( commentService.get ( commentId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Comment does not exist"
            );
        }

        if ( authenticatedUser.get ( ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authenticated"
            );
        }

        if ( ! authenticatedUser.get ( ).get ( ).getId ( ).equals ( commentService.get ( commentId ).get ( ).getCreatedBy ( ).getId ( ) ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authorized to update this comment"
            );
        }

        commentDto.getTicket ( ).setTicketId ( ticketId );
        commentDto.getCreatedBy ( ).setUserId ( authenticatedUser.get ( ).get ( ).getId ( ) );
        return commentService.updateDto ( commentDto );
    }

}
