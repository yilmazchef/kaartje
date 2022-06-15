package be.intecbrussel.data.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.experimental.Accessors;

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
public class Response extends AbstractEntity {

    UUID ticket;

    String createdBy;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String content;

    String priority;

    Float score;

    @ElementCollection
    Set<String> tags = new HashSet<>();

    public Response addTag(@NotNull final String tag){
        this.getTags().add(tag);
    }

    public Response removeTag(@NotNull final String tag){
        this.getTags().remove(tag);
    }

    boolean isActive;

}
