package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.entity.Like;
import org.mapstruct.*;

@Mapper ( componentModel = "spring" )
public interface LikeMapper {
    @Mapping ( source = "likeId", target = "id" )
    Like likeDtoToLike ( LikeDto likeDto );

    @Mapping ( source = "id", target = "likeId" )
    LikeDto likeToLikeDto ( Like like );

    @Mapping ( source = "likeId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Like updateLikeFromLikeDto ( LikeDto likeDto, @MappingTarget Like like );
}
