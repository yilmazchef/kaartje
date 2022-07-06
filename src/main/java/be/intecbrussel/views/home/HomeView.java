package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.TicketDto;
import be.intecbrussel.data.entity.Like;
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

    private static final String ROUTE = "home";
    private final Grid < TicketDto > grid = new Grid <> ( );

    private final HomeApi api;

    public HomeView ( final HomeApi api ) {
        this.api = api;

        addClassName ( "home-view" );
        setSizeFull ( );

        this.grid.setHeight ( "100%" );
        this.grid.addThemeVariants ( GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS );
        this.grid.addComponentColumn ( dto -> {

            final long likesCount = api.getLikesCount ( dto.getTicketId ( ) );
            final long sharesCount = api.getSharesCount ( dto.getTicketId ( ) );
            final long commentsCount = api.getCommentsCount ( dto.getTicketId ( ) );


            final var ticketLayout = new TicketLayout (
                    dto.getCreatedBy ( ).getUsername ( ),
                    dto.getCreatedAt ( ).format ( DateTimeFormatter.ofPattern ( "dd-MM-yyyy HH:mm:ss" ) ),
                    dto.getMessage ( ),
                    dto.getCreatedBy ( ).getProfilePictureUrl ( ),
                    dto.getLikes ( ),
                    dto.getShares ( ).size ( ),
                    dto.getComments ( ).size ( )
            );

            ticketLayout.getLikeButton ( ).addClickListener ( onClick -> {
                final var likeCreated = likeService.create (
                        new Like ( )
                                .setCreatedBy ( authenticatedUser.get ( ).orElseThrow (
                                        ( ) -> new IllegalStateException ( "Authenticated user is not set in the session. Please try to login again." )
                                ) )
                                .setTicket (
                                        ticketService.getOne ( dto.getTicketId ( ) )
                                )
                );

                if ( likeCreated != null ) {
                    Notification.show (
                            MessageFormat.format (
                                    "{0} liked the ticket from {1}",
                                    likeCreated.getCreatedBy ( ).getUsername ( ),
                                    dto.getName ( )
                            )
                            , 3000, Notification.Position.MIDDLE
                    );
                } else {
                    Notification.show ( "You have already liked this ticket before." );
                }


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
