package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "departments", schema = "public")
public class Category {

    @ToString.Include
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    UUID id;

    Boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    @NotNull
    String title;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    User updatedBy;

    String alias;

    public void addTag(@NotNull final String tag) {
        if (this.alias == null) {
            this.alias = tag;
        } else {
            this.alias += "," + tag;
        }
    }

    public void removeTag(@NotNull final String tag) {
        if (this.alias == null) {
            return;
        }
        final String[] alias = this.alias.split(",");
        final StringBuilder sb = new StringBuilder();
        for (final String t : alias) {
            if (!t.equals(tag)) {
                sb.append(t).append(",");
            }
        }
        this.alias = sb.toString();
    }

    public void removeAllTags() {
        this.setAlias("undefined");
    }

    public boolean hasAlias(String alias) {
        return this.alias.contains(alias);
    }

    public boolean hasAliases() {
        return !this.alias.isEmpty();
    }

}
