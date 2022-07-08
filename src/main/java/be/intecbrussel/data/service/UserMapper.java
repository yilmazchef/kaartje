package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.UserDto;
import be.intecbrussel.data.entity.User;
import org.mapstruct.*;

@Mapper ( componentModel = "spring" )
public interface UserMapper {
    @Mapping ( source = "userId", target = "id" )
    User userDtoToUser ( UserDto userDto );

    @Mapping ( source = "id", target = "userId" )
    UserDto userToUserDto ( User user );

    @Mapping ( source = "userId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    User updateUserFromUserDto ( UserDto userDto, @MappingTarget User user );
}
