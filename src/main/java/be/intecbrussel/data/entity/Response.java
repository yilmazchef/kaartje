package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
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
@Table(name = "responses")
public class Response  {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

    Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    @Column(nullable = false)
    @NotNull
    @Email
    String createdBy;

    @FutureOrPresent
    LocalDateTime createdAt;

    @FutureOrPresent
    LocalDateTime updatedAt;

    @Column(nullable = false)
    @NotNull
    String content;

    @Column(nullable = false)
    @Min(value = 1, message = "The value must be greater than 0")
    @Max(value = 5, message = "The value must be less than 5")
    Integer priority;

    @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Max(value = 10, message = "The value must be less than or equal to 10")
    Float score;

    @ElementCollection
    @CollectionTable(name = "tags")
    Set<String> tags = new java.util.LinkedHashSet<>();

    public Response addTag(@NotNull final String tag) {
        this.getTags().add(tag);
        return this;
    }

    public Response removeTag(@NotNull final String tag) {
        this.getTags().remove(tag);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    Ticket ticket;

}
