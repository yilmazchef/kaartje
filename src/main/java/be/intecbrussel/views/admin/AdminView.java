package be.intecbrussel.views.admin;

import be.intecbrussel.data.entity.Ticket;
import be.intecbrussel.data.service.TicketService;
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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import elemental.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.util.UriUtils;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@PageTitle ( "Tickets" )
@Route ( value = "admin/:ticketID?/:action?(edit)", layout = MainLayout.class )
@PermitAll
@Tag ( "admin-view" )
@JsModule ( "./views/admin/admin-view.ts" )
@Uses ( Icon.class )
public class AdminView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String TICKET_ID = "ticketID";
    private final String TICKET_EDIT_ROUTE_TEMPLATE = "admin/%s/edit";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid < Ticket > grid;

    @Id
    private TextField subject;
    @Id
    private Upload attachment;
    @Id
    private Image attachmentPreview;
    @Id
    private TextField content;
    @Id
    private TextField createdBy;
    @Id
    private TextField updatedBy;
    @Id
    private DateTimePicker createdAt;
    @Id
    private DateTimePicker updatedAt;
    @Id
    private TextField status;
    @Id
    private Checkbox isDeleted;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private BeanValidationBinder < Ticket > binder;

    private Ticket ticket;

    private final TicketService ticketService;

    @Autowired
    public AdminView ( TicketService ticketService ) {
        this.ticketService = ticketService;
        addClassNames ( "admin-view" );
        grid.addColumn ( Ticket :: getSubject ).setHeader ( "Subject" ).setAutoWidth ( true );
        LitRenderer < Ticket > attachmentRenderer = LitRenderer
                . < Ticket > of ( "<img style='height: 64px' src=${item.attachment} />" )
                .withProperty ( "attachment", Ticket :: getAttachment );
        grid.addColumn ( attachmentRenderer ).setHeader ( "Attachment" ).setWidth ( "68px" ).setFlexGrow ( 0 );

        grid.addColumn ( Ticket :: getMessage ).setHeader ( "Content" ).setAutoWidth ( true );
        grid.addColumn ( Ticket :: getCreatedBy ).setHeader ( "Created By" ).setAutoWidth ( true );
        grid.addColumn ( Ticket :: getUpdatedBy ).setHeader ( "Updated By" ).setAutoWidth ( true );
        grid.addColumn ( Ticket :: getCreatedAt ).setHeader ( "Created At" ).setAutoWidth ( true );
        grid.addColumn ( Ticket :: getUpdatedAt ).setHeader ( "Updated At" ).setAutoWidth ( true );
        grid.addColumn ( Ticket :: getStatus ).setHeader ( "Status" ).setAutoWidth ( true );
        LitRenderer < Ticket > isActiveRenderer = LitRenderer. < Ticket > of (
                        "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>" )
                .withProperty ( "icon", t -> this.isDeleted.getValue ( ).equals ( Boolean.TRUE ) ? "check" : "minus" ).withProperty ( "color",
                        t -> t.getIsDeleted ( ).equals ( Boolean.TRUE )
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)" );

        grid.addColumn ( isActiveRenderer ).setHeader ( "Is Active" ).setAutoWidth ( true );

        grid.setItems ( query -> ticketService.list (
                        PageRequest.of ( query.getPage ( ), query.getPageSize ( ), VaadinSpringDataHelpers.toSpringDataSort ( query ) ) )
                .stream ( ) );
        grid.addThemeVariants ( GridVariant.LUMO_NO_BORDER );
        grid.setHeightFull ( );

        // when a row is selected or deselected, populate form
        grid.asSingleSelect ( ).addValueChangeListener ( event -> {
            if ( event.getValue ( ) != null ) {
                UI.getCurrent ( ).navigate ( String.format ( TICKET_EDIT_ROUTE_TEMPLATE, event.getValue ( ).getId ( ) ) );
            } else {
                clearForm ( );
                UI.getCurrent ( ).navigate ( AdminView.class );
            }
        } );

        // Configure Form
        binder = new BeanValidationBinder <> ( Ticket.class );

        // Bind fields. This is where you'd define e.g. validation rules

        // TODO: fix the following issues.
        //  2022-06-20 23:43:47.238  INFO 22728 --- [nio-8888-exec-5] com.vaadin.flow.data.binder.Binder       : firstName does not have an accessible setter
        //  2022-06-20 23:43:47.238  INFO 22728 --- [nio-8888-exec-5] com.vaadin.flow.data.binder.Binder       : lastName does not have an accessible setter
        //  2022-06-20 23:43:47.238  INFO 22728 --- [nio-8888-exec-5] com.vaadin.flow.data.binder.Binder       : phone does not have an accessible setter

        binder.bindInstanceFields ( this );

        attachImageUpload ( attachment, attachmentPreview );

        cancel.addClickListener ( e -> {
            clearForm ( );
            refreshGrid ( );
        } );

        save.addClickListener ( e -> {
            try {
                if ( this.ticket == null ) {
                    this.ticket = new Ticket ( );
                }
                binder.writeBean ( this.ticket );
                this.ticket.setAttachment ( attachmentPreview.getSrc ( ) );

                ticketService.update ( this.ticket );
                clearForm ( );
                refreshGrid ( );
                Notification.show ( "Ticket details stored." );
                UI.getCurrent ( ).navigate ( AdminView.class );
            } catch ( ValidationException validationException ) {
                Notification.show ( "An exception happened while trying to store the ticket details." );
            }
        } );
    }

    @Override
    public void beforeEnter ( BeforeEnterEvent event ) {
        Optional < UUID > ticketId = event.getRouteParameters ( ).get ( TICKET_ID ).map ( UUID :: fromString );
        if ( ticketId.isPresent ( ) ) {
            Optional < Ticket > ticketFromBackend = ticketService.get ( ticketId.get ( ) );
            if ( ticketFromBackend.isPresent ( ) ) {
                populateForm ( ticketFromBackend.get ( ) );
            } else {
                Notification.show ( String.format ( "The requested ticket was not found, ID = %s", ticketId.get ( ) ), 3000,
                        Notification.Position.BOTTOM_START );
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid ( );
                event.forwardTo ( AdminView.class );
            }
        }
    }

    private void attachImageUpload ( Upload upload, Image preview ) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream ( );
        upload.setAcceptedFileTypes ( "image/*" );
        upload.setReceiver ( ( fileName, mimeType ) -> {
            return uploadBuffer;
        } );
        upload.addSucceededListener ( e -> {
            String mimeType = e.getMIMEType ( );
            String base64ImageData = Base64.getEncoder ( ).encodeToString ( uploadBuffer.toByteArray ( ) );
            String dataUrl = "data:" + mimeType + ";base64,"
                    + UriUtils.encodeQuery ( base64ImageData, StandardCharsets.UTF_8 );
            upload.getElement ( ).setPropertyJson ( "files", Json.createArray ( ) );
            preview.setSrc ( dataUrl );
            uploadBuffer.reset ( );
        } );
        preview.setVisible ( false );
    }

    private void refreshGrid ( ) {
        grid.select ( null );
        grid.getLazyDataView ( ).refreshAll ( );
    }

    private void clearForm ( ) {
        populateForm ( null );
    }

    private void populateForm ( Ticket value ) {
        this.ticket = value;
        binder.readBean ( this.ticket );
        this.attachmentPreview.setVisible ( value != null );
        if ( value == null ) {
            this.attachmentPreview.setSrc ( "" );
        } else {
            this.attachmentPreview.setSrc ( value.getAttachment ( ) );
        }

    }
}
