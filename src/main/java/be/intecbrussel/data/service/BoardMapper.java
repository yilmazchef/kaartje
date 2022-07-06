package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.BoardDto;
import be.intecbrussel.data.entity.Board;
import org.mapstruct.*;

@Mapper ( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring" )
public interface BoardMapper {
    @Mapping ( source = "boardId", target = "id" )
    Board boardDtoToBoard ( BoardDto boardDto );

    @Mapping ( source = "id", target = "boardId" )
    BoardDto boardToBoardDto ( Board board );

    @Mapping ( source = "boardId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Board updateBoardFromBoardDto ( BoardDto boardDto, @MappingTarget Board board );
}
