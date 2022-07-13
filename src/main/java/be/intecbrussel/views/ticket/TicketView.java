package be.intecbrussel.views.ticket;

import be.intecbrussel.data.entity.*;
import be.intecbrussel.data.service.CommentService;
import be.intecbrussel.data.service.LikeService;
import be.intecbrussel.data.service.ShareService;
import be.intecbrussel.data.service.TicketService;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@PageTitle ( "Ticket" )
@Route ( value = TicketView.ROUTE, layout = MainLayout.class )
@AnonymousAllowed
public class TicketView extends Div implements AfterNavigationObserver {

    public static final String ROUTE = "ticket";
    private final TicketService ticketService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final ShareService shareService;

    private final List < Ticket > tickets = new ArrayList <> ( );

    public TicketView ( final TicketService ticketService, LikeService likeService, CommentService commentService, ShareService shareService ) {
        this.ticketService = ticketService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.shareService = shareService;

        addClassName ( "ticket-view" );
        setSizeFull ( );

        for ( Ticket ticket : tickets ) {
            final var ticketId = ticket.getId();
            final var ticketLayout = new TicketLayout (
                    ticket.getCreatedBy ( ).getUsername ( ),
                    ticket.getCreatedAt ( ),
                    ticket.getMessage ( ),
                    ticket.getCreatedBy ( ).getProfilePictureUrl ( ),
                    likeService.countByTicket ( ticketId ),
                    shareService.countByTicket ( ticketId ),
                    commentService.countByTicket ( ticketId )
            );
            add(ticketLayout);
        }

    }

    @Override
    public void afterNavigation ( AfterNavigationEvent event ) {

        final var oPage = event.getLocationChangeEvent ( ).getQueryParameter ( "page" );
        final var oSize = event.getLocationChangeEvent ( ).getQueryParameter ( "size" );

        final var page = Integer.parseInt ( oPage.orElse ( "0" ) );
        final var size = Integer.parseInt ( oSize.orElse ( "25" ) );

        this.tickets.addAll (
                this.ticketService.list ( PageRequest.of ( page, size ) ).toList ( )
        );
    }

}
