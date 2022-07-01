package be.intecbrussel.views.student;

import be.intecbrussel.data.entity.Ticket;
import be.intecbrussel.data.service.TicketService;
import be.intecbrussel.data.service.UserService;
import be.intecbrussel.security.AuthenticatedUser;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.text.MessageFormat;

@PageTitle ( "Intec | Maak een nieuwe ticket aan.." )
@Route ( value = "student", layout = MainLayout.class )
@PermitAll
@Tag ( "student-view" )
@JsModule ( "./views/student/student-view.ts" )
@Uses ( Icon.class )
public class StudentView extends LitTemplate implements HasStyle {

    private final TicketService ticketService;

    private final AuthenticatedUser authenticatedUser;

    private final UserService userService;

    private FormLayout formLayout;

    @Id
    private TextField subject;

    @Id
    private DatePicker date;

    @Id
    private TextField from;

    @Id
    private TextField to;

    @Id
    private TextArea message;

    @Id
    private Button submit;

    public StudentView ( final TicketService ticketService,
                         final AuthenticatedUser authenticatedUser,
                         final UserService userService ) {
        this.ticketService = ticketService;
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;

        from.setReadOnly ( true );

        message.setMaxLength ( 1000 );
        message.setRequired ( true );

        final var oFromUser = authenticatedUser.get ( );

        oFromUser.ifPresent ( user -> from.setValue ( user.getUsername ( ) ) );

        submit.addClickListener ( onClick -> {

            final var oToUser = this.userService.findByUsername ( to.getValue ( ) );

            if ( oFromUser.isPresent ( ) && oToUser.isPresent ( ) ) {

                final var fromUser = oFromUser.get ( );
                final var toUser = oToUser.get ( );

                this.from.setValue ( fromUser.getUsername ( ) );
                final var newTicketRequest = new Ticket ( )
                        .setSubject ( subject.getValue ( ) )
                        .setMessage ( message.getValue ( ) )
                        .setCreatedBy ( fromUser )
                        .setAssignedTo ( toUser )
                        .setUpdatedBy ( fromUser )
                        .setTags ( "student,issue" );

                final var savedTicketResponse = this.ticketService.create (
                        newTicketRequest
                );

                Notification.show (
                        MessageFormat.format (
                                "Ticket met id {0} is aangemaakt",
                                savedTicketResponse.getId ( )
                        ),
                        3000,
                        Notification.Position.MIDDLE
                );
            }


        } );


    }
}
