package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
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
@Table(name = "shares", schema = "public")
public class Share {

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

    @URL(message = "Invalid URL", regexp = "^(http|https)://.*$")
    String sharedOn;

    Integer viewsCount;

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

        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
    }

}
