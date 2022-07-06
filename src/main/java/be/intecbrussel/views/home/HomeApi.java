package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.dto.TicketDto;
import be.intecbrussel.data.service.*;
import be.intecbrussel.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping ( "/tickets" )
    public Page < TicketDto > getTickets ( @NotNull final Pageable pageable ) {
        return ticketService.listDto ( pageable );
    }

    @RequestMapping ( "/tickets/{ticket_id}/likes" )
    public Page < LikeDto > getLikes ( @NotNull final Pageable pageable, @NotNull final UUID ticketId ) {
        return likeService.listByTicketIdDto ( ticketId, pageable );
    }




}
