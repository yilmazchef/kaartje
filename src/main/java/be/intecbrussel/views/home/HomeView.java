package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.TicketDto;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;
import java.text.MessageFormat;

@PageTitle ( "Home" )
@Route ( value = "home", layout = MainLayout.class )
@RouteAlias ( value = "", layout = MainLayout.class )
@PermitAll
public class HomeView extends Div implements AfterNavigationObserver {

    private static final String ROUTE = "home";
    private final Grid < TicketDto > grid = new Grid <> ( );

    private final HomeClient client;

    public HomeView ( HomeClient client ) {
        this.client = client;

        addClassName ( "home-view" );
        setSizeFull ( );

        this.grid.setHeight ( "100%" );
        this.grid.addThemeVariants ( GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS );
        this.grid.addComponentColumn ( dto -> {

            final var likesCount = this.client.getLikesCount ( dto.getTicketId ( ) );
            final var sharesCount = this.client.getSharesCount ( dto.getTicketId ( ) );
            final var commentsCount = this.client.getCommentsCount ( dto.getTicketId ( ) );

            final var ticketLayout = new TicketLayout (
                    dto.getCreatedBy ( ).getUsername ( ),
                    dto.getCreatedAt ( ),
                    dto.getMessage ( ),
                    dto.getCreatedBy ( ).getProfilePictureUrl ( ),
                    likesCount, sharesCount, commentsCount
            );

            ticketLayout.getLikeButton ( ).addClickListener ( onClick -> {
                final var likeCreated = this.client.likeTicket ( dto.getTicketId ( ) );

                if ( likeCreated != null ) {
                    Notification.show (
                            MessageFormat.format (
                                    "{0} liked the ticket from {1}",
                                    likeCreated.getCreatedBy ( ).getUsername ( ),
                                    dto.getCreatedBy ( ).getUsername ( )
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

        final var ticketBinders = DataProvider.fromStream (
                this.client.getTickets ( page, size ).stream ( ) );


        this.grid.setItems ( ticketBinders );

    }

}
