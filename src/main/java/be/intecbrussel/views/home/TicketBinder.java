package be.intecbrussel.views.home;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketBinder {

    @NotEmpty
    UUID ticketId;

    @URL
    String image;

    @NotEmpty
    String name;

    String date;

    String post;

    String likes;

    String comments;

    String shares;

}
