package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Table(name = "responses")
public class Response {

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

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
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
    Set<String> tags = new HashSet<>();

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

    @PrePersist
    void onCreate() {

        if (this.tags == null) {
            this.tags = new HashSet<>();
            this.getTags().add("default");
        }

        if (this.score == null) {
            this.score = 0f;
        }

        if (this.priority == null) {
            this.priority = 1;
        }

        if (this.isDeleted == null) {
            this.isDeleted = false;
        }

    }

    @PreUpdate
    void onUpdate() {

        if (this.tags == null) {
            this.tags = new HashSet<>();
            this.getTags().add("default");
        }

        if (this.score == null) {
            this.score = 0f;
        }

        if (this.priority == null) {
            this.priority = 1;
        }

        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
    }

}
