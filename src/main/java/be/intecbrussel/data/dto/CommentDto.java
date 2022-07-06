package be.intecbrussel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private UUID commentId;
    private Boolean isDeleted;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull
    private String message;
    @Min ( value = 1, message = "The value must be greater than 0" )
    @Max ( value = 5, message = "The value must be less than 5" )
    private Integer priority;
    @Min ( value = 0, message = "The value must be greater than or equal to 0" )
    @Max ( value = 10, message = "The value must be less than or equal to 10" )
    private Float score;
    private String tags;
    private TicketDto ticket;

    @Override
    public String toString ( ) {
        return this.getMessage ( );
    }
}
