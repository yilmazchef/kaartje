package be.intecbrussel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {
    private UUID categoryId;
    private Boolean isDeleted;
    @NotNull
    private String title;
    private UserDto createdBy;
    private UserDto updatedBy;
    private String alias;
}
