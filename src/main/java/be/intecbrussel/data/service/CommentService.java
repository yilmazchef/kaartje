package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// LOMBOK
@RequiredArgsConstructor
// SPRING
@Service
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Transactional
    public Optional < Comment > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Comment update ( Comment entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Comment > pages ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public List < Comment > list ( ) {
        return repository.findAllActive ( );
    }

    @Transactional
    public Page < Comment > pages ( UUID ticketId, Pageable pageable ) {
        return repository.findAllByTicket ( ticketId, pageable );
    }

    @Transactional
    public List < Comment > list ( UUID ticketId ) {
        return repository.findAllByTicket ( ticketId );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }

    // DTO SERVICES

    @Transactional
    public Optional < CommentDto > getDto ( UUID id ) {

        return repository
                .findById ( id )
                .map ( mapper :: commentToCommentDto );
    }

    @Transactional
    public CommentDto createDto ( CommentDto dto ) {
        return
                mapper.commentToCommentDto (
                        repository.save (
                                mapper.commentDtoToComment ( dto )
                        )
                );
    }

    @Transactional
    public CommentDto updateDto ( CommentDto dto ) {
        return
                mapper.commentToCommentDto (
                        repository.save (
                                mapper.commentDtoToComment ( dto )
                        )
                );
    }

    @Transactional
    public void deleteDto ( @NotNull final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < CommentDto > pagesDto ( @NotNull final Pageable pageable ) {

        return repository
                .findAll ( pageable )
                .map ( mapper :: commentToCommentDto );
    }

    @Transactional
    public List < CommentDto > listDto ( ) {

        return repository
                .findAllActive ( )
                .stream ( )
                .map ( mapper :: commentToCommentDto )
                .collect ( Collectors.toUnmodifiableList ( ) );
    }

    @Transactional
    public Page < CommentDto > listByTicketIdDto ( UUID ticketId, Pageable pageable ) {
        return repository
                .findAllByTicket ( ticketId, pageable )
                .map ( mapper :: commentToCommentDto );
    }

    @Transactional
    public List < CommentDto > listDto ( @NotNull final UUID ticketId ) {

        return repository
                .findAllByTicket ( ticketId )
                .stream ( )
                .map ( mapper :: commentToCommentDto )
                .collect ( Collectors.toUnmodifiableList ( ) );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

}
