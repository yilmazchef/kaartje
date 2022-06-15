package be.intecbrussel.data.entity;

import be.intecbrussel.data.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.URL;

// LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
// LOMBOK -> EXPERIMENTAL
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
// JPA & HIBERNATE
@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {

    String username;

    
    String name;
    @JsonIgnore
    String hashedPassword;
    
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> roles;
    
    // @Lob
    @URL
    String profilePictureUrl;

}
