package be.intecbrussel.data.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Lob;

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
public class Ticket extends AbstractEntity {

    String subject;
    
    @Lob
    String attachment;
    
    String content;

    String createdBy;
    
    String updatedBy;
    
    LocalDateTime createdAt;
    
    LocalDateTime updatedAt;
    
    String status;
    
    boolean isActive;

}
