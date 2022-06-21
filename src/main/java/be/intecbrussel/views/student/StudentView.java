package be.intecbrussel.views.student;

import be.intecbrussel.data.service.TicketService;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PageTitle ( "Intec | Maak een nieuwe ticket aan.." )
@Route ( value = "student", layout = MainLayout.class )
@PermitAll
@Tag ( "student-view" )
@JsModule ( "./views/student/student-view.ts" )
@Uses ( Icon.class )
public class StudentView extends LitTemplate implements HasStyle {

    private final TicketService ticketService;

    @Id
    private FormLayout formLayout;

    @Id
    private TextField title;

    @Id
    private DatePicker date;

    @Id
    private TimePicker from;

    @Id
    private TimePicker to;

    @Autowired
    public StudentView ( TicketService ticketService ) {
        this.ticketService = ticketService;


    }
}
