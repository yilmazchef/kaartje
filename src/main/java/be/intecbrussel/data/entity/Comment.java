package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Table(name = "comments", schema = "public")
public class Comment {

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

    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(nullable = false)
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @NotNull
    String content;

    @Column(nullable = false)
    @Min(value = 1, message = "The value must be greater than 0")
    @Max(value = 5, message = "The value must be less than 5")
    Integer priority;

    @Min(value = 0, message = "The value must be greater than or equal to 0")
    @Max(value = 10, message = "The value must be less than or equal to 10")
    Float score;

    String tags;

    public void addTag(@NotNull final String tag) {
        if (this.tags == null) {
            this.tags = tag;
        } else {
            this.tags += "," + tag;
        }
    }

    public void removeTag(@NotNull final String tag) {
        if (this.tags == null) {
            return;
        }
        final String[] tags = this.tags.split(",");
        final StringBuilder sb = new StringBuilder();
        for (final String t : tags) {
            if (!t.equals(tag)) {
                sb.append(t).append(",");
            }
        }
        this.tags = sb.toString();
    }

    public void removeAllTags() {
        this.setTags("undefined");
    }

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    Ticket ticket;

    @PrePersist
    void onCreate() {

        initializeDefaultValues();

    }

    @PreUpdate
    void onUpdate() {

        initializeDefaultValues();
    }

    private void initializeDefaultValues() {
        if (this.tags == null) {
            this.setTags("undefined");
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
