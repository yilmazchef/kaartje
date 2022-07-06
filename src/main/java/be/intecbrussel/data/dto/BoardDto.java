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
public class BoardDto implements Serializable {
    private UUID boardId;
    private Boolean isDeleted;
    private String title;
    @URL
    private String attachment;
    private String slogan;
    private UserDto createdBy;
    private UserDto updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String color;
    @NotNull
    private String description;
    @URL
    private String background;
}
