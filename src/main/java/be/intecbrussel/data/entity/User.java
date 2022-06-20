package be.intecbrussel.data.entity;

import be.intecbrussel.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "users")
public class User {

    @ToString.Include
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

    Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    @Email
    @NotEmpty
    @ToString.Include
    String username;

    @ToString.Include
    @NotNull
    String phone;

    @ToString.Include
    String firstName;

    @ToString.Include
    String lastName;

    @JsonIgnore
    String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    @URL(message = "Please provide a valid Profile Image URL")
    String profilePictureUrl;

    @PrePersist
    void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.roles == null) {
            this.roles = new HashSet<>();
            this.roles.add(Role.USER);
        }
    }

    @PreUpdate
    void preUpdate() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
            this.roles.add(Role.USER);
        }
    }

}
