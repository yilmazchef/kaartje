package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Table(name = "boards", schema = "public")
public class Board {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

    Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    @ToString.Include
    String title;

    @Lob
    @URL
    String attachment;

    @ToString.Include
    String slogan;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    User updatedBy;

    @ToString.Include
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ToString.Include
    String status;

    String color;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @NotNull
    String description;

    @URL
    String background;

}
