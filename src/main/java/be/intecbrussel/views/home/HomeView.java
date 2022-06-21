package be.intecbrussel.views.home;

import be.intecbrussel.data.entity.Like;
import be.intecbrussel.data.service.*;
import be.intecbrussel.security.AuthenticatedUser;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.*;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

@PageTitle ( "Home" )
@Route ( value = "home", layout = MainLayout.class )
@RouteAlias ( value = "", layout = MainLayout.class )
@PermitAll
public class HomeView extends Div implements AfterNavigationObserver {

    private final Grid < TicketBinder > grid = new Grid <> ( );
    private final TicketService ticketService;
    private final AuthenticatedUser authenticatedUser;

    private final UserService userService;
    private final LikeService likeService;
    private final ShareService shareService;

    private final CommentService commentService;

    public HomeView (
            final TicketService ticketService,
            final AuthenticatedUser authenticatedUser,
            final UserService userService,
            final LikeService likeService,
            final ShareService shareService,
            final CommentService commentService
    ) {
        this.ticketService = ticketService;
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;
        this.likeService = likeService;
        this.shareService = shareService;
        this.commentService = commentService;

        addClassName ( "home-view" );
        setSizeFull ( );
        this.grid.setHeight ( "100%" );
        this.grid.addThemeVariants ( GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS );
        this.grid.addComponentColumn ( ticketBinder -> {
            final TicketLayout ticketLayout = new TicketLayout ( ticketBinder );
            ticketLayout.getLikeButton ( ).addClickListener ( onClick -> {
                final var likeCreated = likeService.create (
                        new Like ( )
                                .setCreatedBy ( authenticatedUser.get ( ).orElseThrow (
                                        ( ) -> new IllegalStateException ( "Authenticated user is not set in the session. Please try to login again." )
                                ) )
                                .setTicket (
                                        ticketService.getOne ( ticketBinder.getTicketId ( ) )
                                )
                );

                Notification.show (
                        MessageFormat.format (
                                "{0} liked the ticket from {1}",
                                likeCreated.getCreatedBy ( ).getUsername ( ),
                                ticketBinder.getName ( )
                        )
                        , 3000, Notification.Position.MIDDLE
                );

            } );

            return ticketLayout;
        } );

        this.add ( this.grid );
    }

    @Override
    public void afterNavigation ( AfterNavigationEvent event ) {

        final var oPage = event.getLocationChangeEvent ( ).getQueryParameter ( "page" );
        final var oSize = event.getLocationChangeEvent ( ).getQueryParameter ( "size" );

        final var page = Integer.parseInt ( oPage.orElse ( "0" ) );
        final var size = Integer.parseInt ( oSize.orElse ( "25" ) );
        final var pagination = PageRequest.of ( page, size );

        final var ticketBinders = DataProvider.fromStream (
                this.ticketService
                        .list ( pagination )
                        .getContent ( )
                        .stream ( )
                        .map ( ticket -> {

                            final var oUser = this.userService.findByUsername ( ticket.getCreatedBy ( ).getUsername ( ) );
                            final var comments = this.commentService.list ( ticket.getId ( ) );
                            final var shares = this.shareService.list ( ticket.getId ( ) );

                            return oUser.map ( user -> new TicketBinder (
                                    ticket.getId ( ),
                                    user.getProfilePictureUrl ( ),
                                    user.getUsername ( ),
                                    ticket.getCreatedAt ( ).format ( DateTimeFormatter.ofPattern ( "dd/MM/yyyy HH:mm:ss" ) ),
                                    ticket.getMessage ( ),
                                    String.valueOf ( ticket.getLikes ( ) ),
                                    comments.size ( ) + "",
                                    String.valueOf ( shares.size ( ) )
                            ) ).orElseGet ( ( ) -> new TicketBinder (
                                    ticket.getId ( ),
                                    "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200",
                                    ticket.getStatus ( ),
                                    ticket.getCreatedAt ( ).format ( DateTimeFormatter.ofPattern ( "dd/MM/yyyy HH:mm:ss" ) ),
                                    ticket.getMessage ( ),
                                    String.valueOf ( ticket.getLikes ( ) ),
                                    comments.size ( ) + "",
                                    String.valueOf ( shares.size ( ) )
                            ) );

                        } ) );

        this.grid.setItems ( ticketBinders );

    }

}
