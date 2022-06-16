package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
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
    @CreatedBy
    String createdBy;

    @Email
    @NotNull
    @LastModifiedBy
    String updatedBy;

    @ElementCollection
    Set<String> alias = new HashSet<>();

    public void addAlias(String alias) {
        this.alias.add(alias);
    }

    public void removeAlias(String alias) {
        this.alias.remove(alias);
    }

    public void removeAllAliases() {
        this.alias.clear();
    }

    public boolean hasAlias(String alias) {
        return this.alias.contains(alias);
    }

    public boolean hasAliases() {
        return !this.alias.isEmpty();
    }


    @OneToOne
    User manager;

}
