package be.intecbrussel.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

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
public class SamplePerson extends AbstractEntity {

    String firstName;
    
    String lastName;
    
    String email;
    
    String phone;
    
    LocalDate dateOfBirth;
    
    String occupation;
    
    boolean important;

}
