package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

// LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
// LOMBOK -> EXPERIMENTAL
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
// JPA & HIBERNATE
@Entity
@Table(name = "departments")
public class Department {

    @ToString.Include
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

    Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    @NotNull
    String title;

    @Email
    @NotNull
    String createdBy;

    @Email
    @NotNull
    String updatedBy;

    @ElementCollection
    Set<String> alias;

    @OneToOne
    User manager;

}
