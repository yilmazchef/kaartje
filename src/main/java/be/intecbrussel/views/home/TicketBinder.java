package be.intecbrussel.views.home;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketBinder {

    String image;
    String name;
    String date;
    String post;
    String likes;
    String comments;
    String shares;

}
