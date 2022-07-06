package be.intecbrussel.views.admin;

import be.intecbrussel.data.service.TicketService;
import be.intecbrussel.security.AuthenticatedUser;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle ( "Tickets" )
@Route ( value = "admin", layout = MainLayout.class )
@RolesAllowed ( {"ADMIN"} )
@Tag ( "admin-view" )
@JsModule ( "./views/admin/admin-view.ts" )
@Uses ( Icon.class )
public class AdminView extends LitTemplate implements HasStyle {

    private final TicketService ticketService;

    private final AuthenticatedUser authenticatedUser;

    public AdminView ( TicketService ticketService, AuthenticatedUser authenticatedUser ) {
        this.ticketService = ticketService;
        this.authenticatedUser = authenticatedUser;

        addClassNames ( "admin-view" );

    }
}
