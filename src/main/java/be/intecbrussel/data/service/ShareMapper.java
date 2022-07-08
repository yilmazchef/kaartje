package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.entity.Share;
import org.mapstruct.*;

@Mapper ( componentModel = "spring" )
public interface ShareMapper {
    @Mapping ( source = "shareId", target = "id" )
    Share shareDtoToShare ( ShareDto shareDto );

    @Mapping ( source = "id", target = "shareId" )
    ShareDto shareToShareDto ( Share share );

    @Mapping ( source = "shareId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Share updateShareFromShareDto ( ShareDto shareDto, @MappingTarget Share share );
}
