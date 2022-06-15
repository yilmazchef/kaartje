package be.intecbrussel.data.entity;

import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// LOMBOK -> EXPERIMENTAL
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
// JPA & HIBERNATE
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

}
