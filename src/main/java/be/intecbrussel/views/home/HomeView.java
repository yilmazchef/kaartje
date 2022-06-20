package be.intecbrussel.views.home;

import be.intecbrussel.data.service.CommentService;
import be.intecbrussel.data.service.ShareService;
import be.intecbrussel.data.service.TicketService;
import be.intecbrussel.data.service.UserService;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.*;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends Div implements AfterNavigationObserver {

    private final Grid<TicketBinder> grid = new Grid<>();
    private final TicketService ticketService;
    private final UserService userService;

    private final ShareService shareService;

    private final CommentService commentService;

    public HomeView(final TicketService ticketService, UserService userService, ShareService shareService, CommentService commentService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.shareService = shareService;
        this.commentService = commentService;

        addClassName("home-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(ut -> new TicketLayout(ut));

        add(grid);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        final var ticketBinders = ticketService
                .list(PageRequest.of(0, 25))
                .getContent()
                .stream()
                .map(ticket -> {

                    final var oUser = userService.getByUsername(ticket.getCreatedBy().getUsername());
                    final var comments = commentService.list(ticket.getId(), PageRequest.of(0, 25));
                    final var shares = shareService.list(ticket.getId(), PageRequest.of(0, 25));

                    return oUser.map(user -> new TicketBinder(
                            user.getProfilePictureUrl(),
                            user.getUsername(),
                            ticket.getCreatedAt().toString(),
                            ticket.getMessage(),
                            String.valueOf(ticket.getLikes()),
                            comments.getTotalElements() + "",
                            String.valueOf(shares.getTotalElements())
                    )).orElseGet(() -> new TicketBinder(
                            "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200",
                            ticket.getStatus(),
                            ticket.getCreatedAt().toString(),
                            ticket.getMessage(),
                            String.valueOf(ticket.getLikes()),
                            comments.getTotalElements() + "",
                            String.valueOf(shares.getTotalElements())
                    ));

                });

        grid.setItems(DataProvider.fromStream(ticketBinders));

    }

}
