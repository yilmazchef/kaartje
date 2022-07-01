package be.intecbrussel.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

// LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString ( onlyExplicitlyIncluded = true )
// LOMBOK -> EXPERIMENTAL
@FieldDefaults ( level = AccessLevel.PRIVATE )
@Accessors ( chain = true )
// JPA & HIBERNATE
@Entity
@Table ( name = "tickets", schema = "public" )
public class Ticket {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    @Type ( type = "uuid-char" )
    UUID id;

    Boolean isDeleted;

    public void delete ( ) {
        this.isDeleted = true;
    }

    @ToString.Include
    String subject;

    @Lob
    @URL
    String attachment;

    @ToString.Include
    @NotNull
    @Lob
    @Type ( type = "org.hibernate.type.TextType" )
    String message;

    @ManyToOne
    @JoinColumn ( name = "created_by" )
    User createdBy;

    @ManyToOne
    @JoinColumn ( name = "updated_by" )
    User updatedBy;

    @ManyToOne
    @JoinColumn ( name = "assigned_to" )
    User assignedTo;

    @EqualsAndHashCode.Include
    @ToString.Include
    LocalDateTime createdAt;

    @ToString.Include
    LocalDateTime updatedAt;

    @ToString.Include
    String status;

    @ToString.Include
    Integer priority;

    @ToString.Include
    String type;

    @ToString.Include
    String tags;

    Integer likes;


    public void addTag ( @NotNull final String tag ) {
        if ( this.tags == null ) {
            this.tags = tag;
        } else {
            this.tags += "," + tag;
        }
    }

    public void removeTag ( @NotNull final String tag ) {
        if ( this.tags == null ) {
            return;
        }
        final String[] tags = this.tags.split ( "," );
        final StringBuilder sb = new StringBuilder ( );
        for ( final String t : tags ) {
            if ( ! t.equals ( tag ) ) {
                sb.append ( t ).append ( "," );
            }
        }
        this.tags = sb.toString ( );
    }

    public void removeAllTags ( ) {
        this.setTags ( "undefined" );
    }

    @ManyToOne
    @JoinColumn ( name = "board_id" )
    Board board;

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass ( this ) != Hibernate.getClass ( o ) ) return false;
        Ticket ticket = ( Ticket ) o;
        return id != null && Objects.equals ( id, ticket.id );
    }

    @Override
    public int hashCode ( ) {
        return getClass ( ).hashCode ( );
    }

    @PrePersist
    public void prePersist ( ) {
        this.setCreatedAt ( LocalDateTime.now ( ) );
        this.setIsDeleted ( false );
        this.setLikes ( 0 );
        this.setPriority ( 1 );
        this.setType ( "REQUEST" );
    }

    @PreUpdate
    public void preUpdate ( ) {
        this.setUpdatedAt ( LocalDateTime.now ( ) );
    }
}
