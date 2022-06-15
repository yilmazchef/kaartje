package be.intecbrussel.views.departments;

import be.intecbrussel.data.entity.Department;
import be.intecbrussel.data.service.DepartmentService;
import be.intecbrussel.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.PermitAll;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Departments")
@Route(value = "departments/:departmentID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
@Tag("departments-view")
@JsModule("./views/departments/departments-view.ts")
public class DepartmentsView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String DEPARTMENT_ID = "departmentID";
    private final String DEPARTMENT_EDIT_ROUTE_TEMPLATE = "departments/%s/edit";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid<Department> grid;

    @Id
    private TextField title;
    @Id
    private TextField createdBy;
    @Id
    private TextField updatedBy;
    @Id
    private TextField alias;
    @Id
    private TextField contactEmail;
    @Id
    private TextField contactPhone;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private BeanValidationBinder<Department> binder;

    private Department department;

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentsView(DepartmentService departmentService) {
        this.departmentService = departmentService;
        addClassNames("departments-view");
        grid.addColumn(Department::getTitle).setHeader("Title").setAutoWidth(true);
        grid.addColumn(Department::getCreatedBy).setHeader("Created By").setAutoWidth(true);
        grid.addColumn(Department::getUpdatedBy).setHeader("Updated By").setAutoWidth(true);
        grid.addColumn(Department::getAlias).setHeader("Alias").setAutoWidth(true);
        grid.addColumn(Department::getManager).setHeader("Manager").setAutoWidth(true);
        grid.setItems(query -> departmentService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(DEPARTMENT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(DepartmentsView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Department.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.department == null) {
                    this.department = new Department();
                }
                binder.writeBean(this.department);

                departmentService.update(this.department);
                clearForm();
                refreshGrid();
                Notification.show("Department details stored.");
                UI.getCurrent().navigate(DepartmentsView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the department details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> departmentId = event.getRouteParameters().get(DEPARTMENT_ID).map(UUID::fromString);
        if (departmentId.isPresent()) {
            Optional<Department> departmentFromBackend = departmentService.get(departmentId.get());
            if (departmentFromBackend.isPresent()) {
                populateForm(departmentFromBackend.get());
            } else {
                Notification.show(String.format("The requested department was not found, ID = %s", departmentId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(DepartmentsView.class);
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

    private void populateForm(Department value) {
        this.department = value;
        binder.readBean(this.department);

    }
}
