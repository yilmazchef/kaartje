package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.entity.Comment;
import org.mapstruct.*;

@Mapper ( componentModel = "spring" )
public interface CommentMapper {
    @Mapping ( source = "commentId", target = "id" )
    Comment commentDtoToComment ( CommentDto commentDto );

    @Mapping ( source = "id", target = "commentId" )
    CommentDto commentToCommentDto ( Comment comment );

    @Mapping ( source = "commentId", target = "id" )
    @BeanMapping ( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    Comment updateCommentFromCommentDto ( CommentDto commentDto, @MappingTarget Comment comment );
}
