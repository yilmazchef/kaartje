package be.intecbrussel.views.responses;

import be.intecbrussel.data.entity.Response;
import be.intecbrussel.data.service.ResponseService;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToUuidConverter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Responses")
@Route(value = "responses/:responseID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
@Tag("responses-view")
@JsModule("./views/responses/responses-view.ts")
@Uses(Icon.class)
public class ResponsesView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String RESPONSE_ID = "responseID";
    private final String RESPONSE_EDIT_ROUTE_TEMPLATE = "responses/%s/edit";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid<Response> grid;

    @Id
    private TextField ticket;

    @Id
    private TextField createdBy;

    @Id
    private DateTimePicker createdAt;

    @Id
    private DateTimePicker updatedAt;

    @Id
    private TextField content;

    @Id
    private TextField priority;

    @Id
    private TextField score;

    @Id
    private TextField tags;

    @Id
    private Checkbox isDeleted;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private final BeanValidationBinder<Response> binder;

    private Response response;

    private final ResponseService responseService;

    public ResponsesView(final ResponseService responseService) {
        this.responseService = responseService;

        addClassNames("responses-view");

        grid.addColumn(tic -> tic.getTicket().getId()).setHeader("Ticket").setAutoWidth(true);
        grid.addColumn(Response::getCreatedBy).setHeader("Created By").setAutoWidth(true);
        grid.addColumn(Response::getCreatedAt).setHeader("Created At").setAutoWidth(true);
        grid.addColumn(Response::getUpdatedAt).setHeader("Updated At").setAutoWidth(true);
        grid.addColumn(Response::getContent).setHeader("Content").setAutoWidth(true);
        grid.addColumn(Response::getPriority).setHeader("Priority").setAutoWidth(true);
        grid.addColumn(Response::getScore).setHeader("Score").setAutoWidth(true);
        grid.addColumn(Response::getTags).setHeader("Tags").setAutoWidth(true);

        final var isDeletedRenderer = LitRenderer.<Response>of(
                        "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon",
                        r -> r.getIsDeleted() ? "check" : "minus").withProperty("color",
                        r -> r.getIsDeleted() ? "var(--lumo-primary-text-color)" : "var(--lumo-disabled-text-color)");

        grid.addColumn(isDeletedRenderer).setHeader("Is Deleted").setAutoWidth(true);

        grid.setItems(query -> responseService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(RESPONSE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ResponsesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Response.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(ticket).withConverter(new StringToUuidConverter("Invalid UUID")).bind("ticket");
        binder.forField(score).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("score");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.response == null) {
                    this.response = new Response();
                }
                binder.writeBean(this.response);

                responseService.update(this.response);
                clearForm();
                refreshGrid();
                Notification.show("Response details stored.");
                UI.getCurrent().navigate(ResponsesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the response details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> responseId = event.getRouteParameters().get(RESPONSE_ID).map(UUID::fromString);
        if (responseId.isPresent()) {
            Optional<Response> responseFromBackend = responseService.get(responseId.get());
            if (responseFromBackend.isPresent()) {
                populateForm(responseFromBackend.get());
            } else {
                Notification.show(String.format("The requested response was not found, ID = %s", responseId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ResponsesView.class);
            }
        }
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Response value) {
        this.response = value;
        binder.readBean(this.response);

    }
}
