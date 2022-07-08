package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.dto.TicketDto;
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
@RequestMapping ( "/api/home" )
public class HomeApi implements IHomeApi {

    private final TicketService ticketService;
    private final AuthenticatedUser authenticatedUser;
    private final UserService userService;
    private final LikeService likeService;
    private final ShareService shareService;
    private final CommentService commentService;

    public static final String GET_TICKETS_END_POINT = "/tickets";
    public static final String GET_LIKES_END_POINT = "/tickets/{ticket_id}/likes";
    public static final String GET_SHARES_END_POINT = "/tickets/{ticket_id}/shares";
    public static final String GET_COMMENTS_END_POINT = "/tickets/{ticket_id}/comments";
    public static final String GET_TICKET_END_POINT = "/tickets/{ticket_id}";
    public static final String GET_TICKET_LIKES_END_POINT = "/tickets/{ticket_id}/likes";
    public static final String GET_TICKET_SHARES_END_POINT = "/tickets/{ticket_id}/shares";
    public static final String GET_TICKET_COMMENTS_END_POINT = "/tickets/{ticket_id}/comments";
    public static final String GET_TICKETS_COUNT_END_POINT = "/tickets/count";
    public static final String GET_TICKET_LIKES_COUNT_END_POINT = "/tickets/{ticket_id}/likes/count";
    public static final String GET_TICKET_SHARES_COUNT_END_POINT = "/tickets/{ticket_id}/shares/count";
    public static final String GET_TICKET_COMMENTS_COUNT_END_POINT = "/tickets/{ticket_id}/comments/count";

    @Override
    @GetMapping ( value = GET_TICKETS_END_POINT )
    public List < TicketDto > getTickets ( @NotNull final int page, @NotNull final int size ) {
        return ticketService.listDto ( PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = GET_LIKES_END_POINT )
    public List < LikeDto > getLikes ( @NotNull final int page, @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.listByTicketIdDto ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = GET_TICKET_LIKES_COUNT_END_POINT )
    public long getLikesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return likeService.countByTicketDto ( ticketId );
    }

    @Override
    @GetMapping ( value = GET_SHARES_END_POINT )
    public List < ShareDto > getShares ( @RequestParam @NotNull final int page, @RequestParam @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.listByTicketIdDto ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = GET_TICKET_SHARES_COUNT_END_POINT )
    public long getSharesCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return shareService.countByTicketDto ( ticketId );
    }

    @Override
    @GetMapping ( value = GET_COMMENTS_END_POINT )
    public List < CommentDto > getComments ( @RequestParam @NotNull final int page, @RequestParam @NotNull final int size, @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.listByTicketIdDto ( ticketId, PageRequest.of ( page, size ) ).toList ();
    }

    @Override
    @GetMapping ( value = GET_TICKET_COMMENTS_COUNT_END_POINT )
    public long getCommentsCount ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId ) {
        return commentService.countByTicketDto ( ticketId );
    }

    @Override
    @PostMapping ( value = "/tickets/{ticket_id}/comments/create" )
    public CommentDto postComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId, @RequestBody @NotNull final CommentDto commentDto ) {

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

    @Override
    @PutMapping ( value = "/tickets/{ticket_id}/comments/update/{comment_id}" )
    public CommentDto updateComment ( @NotNull @PathVariable ( "ticket_id" ) final UUID ticketId,
                                      @NotNull @PathVariable ( "comment_id" ) final UUID commentId,
                                      @RequestBody @NotNull final CommentDto commentDto ) {

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

        commentDto.getTicket ( ).setTicketId ( ticketId );
        commentDto.getCreatedBy ( ).setUserId ( authenticatedUser.get ( ).get ( ).getId ( ) );
        return commentService.updateDto ( commentDto );
    }

    @Override
    @PostMapping ( value = "tickets/{ticket_id}/likes/create" )
    public LikeDto likeTicket ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId, @NotNull @Valid final LikeDto likeDto ) {
        if ( ticketService.get ( ticketId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        return likeService.createDto ( likeDto );
    }

    @Override
    @PostMapping ( value = "tickets/{ticket_id}/likes/create/quick" )
    public LikeDto likeTicket ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId ) {

        final var oTicket = ticketService.getDTO ( ticketId );
        if ( oTicket.isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        final var ticket = oTicket.get ( );
        final var likeDto = new LikeDto ( );
        likeDto.setTicket ( ticket );

        if ( authenticatedUser.get ( ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "User is not authenticated."
            );
        }

        final var oCreatedBy = userService.getDto ( authenticatedUser.get ( ).get ( ).getId ( ) );

        if ( oCreatedBy.isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "The authenticated user is NOT bound with an existing user."
            );
        }

        final var createdBy = oCreatedBy.get ( );
        likeDto.setCreatedBy ( createdBy );

        final var now = LocalDateTime.now ( );
        likeDto.setCreatedAt ( now );
        likeDto.setIsDeleted ( false );
        likeDto.setUpdatedAt ( now );

        return likeService.createDto ( likeDto );
    }

    @Override
    @PostMapping ( "tickets/{ticket_id}/likes/delete/{like_id}" )
    public LikeDto deleteLike ( @PathVariable ( "ticket_id" ) @NotNull final UUID ticketId,
                                @PathVariable ( "like_id" ) @NotNull final UUID likeId ) {

        if ( ticketService.get ( ticketId ).isEmpty ( ) ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Ticket does not exist"
            );
        }

        final var oLikeDto = likeService.getDto ( likeId );
        if ( oLikeDto.isEmpty ( ) ) {
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

        final var likeDto = oLikeDto.get ( );

        if ( ! Objects.equals (
                authenticatedUser.get ( ).get ( ).getId ( ),
                likeDto.getCreatedBy ( ).getUserId ( ) )
        ) {
            throw new ResponseStatusException (
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "You are not authorized to delete this like"
            );
        }

        likeDto.setIsDeleted ( true );
        return likeService.updateDto ( likeDto );
    }

}
