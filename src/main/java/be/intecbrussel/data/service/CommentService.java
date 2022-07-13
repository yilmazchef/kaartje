package be.intecbrussel.data.service;

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

    @Transactional
    public Optional < Comment > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Comment create ( Comment entity ) {
        return repository.save ( entity );
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


    @Transactional
    public int countByTicket( UUID ticketId ) {
        return ( int ) repository.countByTicket ( ticketId );
    }

}
