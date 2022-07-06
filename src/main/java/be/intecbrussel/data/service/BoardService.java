package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.BoardDto;
import be.intecbrussel.data.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;


// LOMBOK
@RequiredArgsConstructor
// SPRING
@Service
public class BoardService {

    private final BoardRepository repository;
    private final BoardMapper mapper;

    @Transactional
    public Optional < Board > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Board update ( Board entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Board > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }


    // DTO SERVICES

    @Transactional
    public Optional < BoardDto > getDto ( @NotEmpty final UUID id ) {
        return repository
                .findById ( id )
                .map ( mapper :: boardToBoardDto );
    }

    @Transactional
    public BoardDto updateDto ( BoardDto dto ) {
        return mapper.boardToBoardDto (
                repository.save (
                        mapper.boardDtoToBoard ( dto )
                )
        );
    }

    @Transactional
    public void deleteDto ( @NotEmpty final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < BoardDto > listDto ( @NotNull Pageable pageable ) {
        return repository
                .findAll ( pageable )
                .map ( mapper :: boardToBoardDto );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

}
