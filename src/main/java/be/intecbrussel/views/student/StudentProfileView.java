package be.intecbrussel.views.student;

import be.intecbrussel.data.entity.User;
import be.intecbrussel.data.service.UserService;
import be.intecbrussel.security.AuthenticatedUser;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.text.MessageFormat;

@PageTitle ( "Intec | Profiel Student" )
@Route ( value = "student/profile", layout = MainLayout.class )
@PermitAll
@Tag ( "student-profile-view" )
@JsModule ( "./views/student/student-profile-view.ts" )
@Uses ( Icon.class )
public class StudentProfileView extends LitTemplate implements HasStyle {

    private final AuthenticatedUser authenticatedUser;

    private final UserService userService;

    private FormLayout formLayout;

    @Id
    private TextField firstName;

    @Id
    private TextField lastName;

    @Id
    private TextField email;

    @Id
    private TextField phone;

    @Id
    private Button submit;

    public StudentProfileView ( final AuthenticatedUser authenticatedUser,
                                final UserService userService ) {
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;

        lastName.setReadOnly ( true );

        final var oUser = authenticatedUser.get ( );

        final var userProfile = oUser.map (
                existingUser -> {
                    if ( existingUser.getFirstName ( ) != null && firstName.getValue ( ).isEmpty ( ) ) {
                        firstName.setValue ( existingUser.getFirstName ( ) );
                    }

                    if ( existingUser.getLastName ( ) != null && lastName.getValue ( ).isEmpty ( ) ) {
                        lastName.setValue ( existingUser.getLastName ( ) );
                    }

                    if ( existingUser.getUsername ( ) != null && email.getValue ( ).isEmpty ( ) ) {
                        email.setValue ( existingUser.getUsername ( ) );
                    }

                    if ( existingUser.getPhone ( ) != null && phone.getValue ( ).isEmpty ( ) ) {
                        phone.setValue ( existingUser.getPhone ( ) );
                    }

                    return existingUser;
                }
        ).orElse (
                new User ( )
                        .setFirstName ( firstName.getValue ( ) )
                        .setLastName ( lastName.getValue ( ) )
                        .setUsername ( email.getValue ( ) )
                        .setPhone ( phone.getValue ( ) )
        );

        submit.addClickListener ( onClick -> {
            final var updatedOrSavedUser = this.userService.update (
                    userProfile
            );

            Notification.show (
                    MessageFormat.format (
                            "Gebruiker {0} {1} is opgeslagen",
                            updatedOrSavedUser.getFirstName ( ),
                            updatedOrSavedUser.getLastName ( )

                    )
            );
        } );

    }
}
