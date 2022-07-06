package be.intecbrussel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto implements Serializable {
    private UUID likeId;
    private Boolean isDeleted;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TicketDto ticket;

    @Override
    public String toString(){
        return this.getCreatedBy ().toString () + " liked " + this.getTicket ().toString ();
    }
}
