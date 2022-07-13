package be.intecbrussel.views.home;

import be.intecbrussel.views.MainLayout;
import be.intecbrussel.views.ticket.TicketClient;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle ( "Home" )
@Route ( value = HomeView.ROUTE, layout = MainLayout.class )
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public static final String ROUTE = "";
    private final TicketClient client;

    public HomeView ( final TicketClient client ) {
        this.client = client;

        addClassName ( "home-view" );
        setSizeFull ( );


    }

}
