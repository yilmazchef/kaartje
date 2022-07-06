package be.intecbrussel.data.dto;

import be.intecbrussel.data.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private UUID userId;
    private Boolean isDeleted;
    @Email
    @NotEmpty
    private String username;
    @NotNull
    private String phone;
    private String firstName;
    private String lastName;
    private Set < Role > roles = new HashSet <> ( );
    @URL ( message = "Please provide a valid Profile Image URL" )
    private String profilePictureUrl;

    @Override
    public String toString ( ) {
        return this.getFirstName ( ) + " " + this.getLastName ( );
    }
}
