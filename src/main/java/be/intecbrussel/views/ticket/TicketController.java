package be.intecbrussel.views.ticket;

import be.intecbrussel.data.entity.*;
import be.intecbrussel.data.service.*;
import be.intecbrussel.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// LOMBOK
@RequiredArgsConstructor
// SPRING
@RestController
@RequestMapping ( TicketEndpoints.HOME_BASE_END_POINT )
public class TicketController implements ITicketApi {

    private final TicketService ticketService;
    private final AuthenticatedUser authenticatedUser;
    private final UserService userService;
    private final LikeService likeService;
    private final ShareService shareService;
    private final CommentService commentService;


    @Override
    @GetMapping ( value = TicketEndpoints.GET_TICKETS_END_POINT )
    public List < Ticket > getTickets ( @NotNull final int page, @NotNull final int size ) {
        return ticketService.list ( PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_LIKES_END_POINT )
    public List < Like > getLikes ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.listByTicketId ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_TICKET_LIKES_COUNT_END_POINT )
    public long getLikesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.countByTicket ( ticketId );
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_SHARES_END_POINT )
    public List < Share > getShares ( @RequestParam @NotNull final int page, @RequestParam @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.listByTicketId ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_TICKET_SHARES_COUNT_END_POINT )
    public long getSharesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.countByTicket ( ticketId );
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_COMMENTS_END_POINT )
    public List < Comment > getComments ( @RequestParam @NotNull final int page, @RequestParam @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.listByTicketId ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = TicketEndpoints.GET_TICKET_COMMENTS_COUNT_END_POINT )
    public long getCommentsCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.countByTicket ( ticketId );
    }

    @Override
    @PostMapping ( value = "/tickets/{ticket_id}/comments/create" )
    public Comment postComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId, @RequestBody @NotNull final Comment comment ) {

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

        comment.getTicket ( ).setId ( ticketId );
        comment.getCreatedBy ( ).setId ( authenticatedUser.get ( ).get ( ).getId ( ) );
        return commentService.create ( comment );
    }

    @Override
    @PutMapping ( value = "/tickets/{ticket_id}/comments/update/{comment_id}" )
    public Comment updateComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId,
                                      @NotNull @PathVariable ( "comment_id" ) final UUID commentId,
                                      @RequestBody @NotNull final Comment comment ) {

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

        if ( ! Objects.equals (
                authenticatedUser.get ( ).get ( ).getId ( ),
                commentService.get ( commentId ).get ( ).getCreatedBy ( ).getId ( ) )
        ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authorized to update this comment"
            );
        }

        comment.getTicket ( ).setTicketId ( ticketId );
        comment.getCreatedBy ( ).setUserId ( authenticatedUser.get ( ).get ( ).getId ( ) );
        return commentService.update ( comment );
    }

    @Override
    @PostMapping ( value = "tickets/{ticket_id}/likes/create" )
    public Like likeTicket ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId, @NotNull @Valid final Like like ) {
        if ( ticketService.get ( ticketId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        return likeService.create ( like );
    }

    @Override
    @PostMapping ( value = "tickets/{ticket_id}/likes/create/quick" )
    public Like likeTicket ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId ) {

        final var oTicket = ticketService.getDTO ( ticketId );
        if ( oTicket.isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        final var ticket = oTicket.get ( );
        final var like = new Like ( );
        like.setTicket ( ticket );

        if ( authenticatedUser.get ( ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User is not authenticated."
            );
        }

        final var oCreatedBy = userService.get ( authenticatedUser.get ( ).get ( ).getId ( ) );

        if ( oCreatedBy.isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "The authenticated user is NOT bound with an existing user."
            );
        }

        final var createdBy = oCreatedBy.get ( );
        like.setCreatedBy ( createdBy );

        final var now = LocalDateTime.now ( );
        like.setCreatedAt ( now );
        like.setIsDeleted ( false );
        like.setUpdatedAt ( now );

        return likeService.create ( like );
    }

    @Override
    @PostMapping ( "tickets/{ticket_id}/likes/delete/{like_id}" )
    public Like deleteLike ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId,
                                @PathVariable ( "like_id" ) @NotNull final UUID likeId ) {

        if ( ticketService.get ( ticketId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        final var oLike = likeService.get ( likeId );
        if ( oLike.isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Like does not exist"
            );
        }

        if ( authenticatedUser.get ( ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authenticated"
            );
        }

        final var like = oLike.get ( );

        if ( ! Objects.equals (
                authenticatedUser.get ( ).get ( ).getId ( ),
                like.getCreatedBy ( ).getUserId ( ) )
        ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authorized to delete this like"
            );
        }

        like.setIsDeleted ( true );
        return likeService.update ( like );
    }

}
