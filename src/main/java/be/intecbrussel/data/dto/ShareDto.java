package be.intecbrussel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDto implements Serializable {
    private UUID shareId;
    private Boolean isDeleted;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @URL ( message = "Invalid URL", regexp = "^(http|https)://.*$" )
    private String sharedOn;
    private Integer viewsCount;
    private TicketDto ticket;
}
