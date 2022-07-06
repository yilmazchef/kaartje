package be.intecbrussel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto implements Serializable {
    private UUID ticketId;
    private Boolean isDeleted;
    private String subject;
    @URL
    private String attachment;
    @NotNull
    private String message;
    private UserDto createdBy;
    private UserDto updatedBy;
    private UserDto assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private Integer priority;
    private String type;
    private String tags;
    private Integer likes;
    private BoardDto board;
}
