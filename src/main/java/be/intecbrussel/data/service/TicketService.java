package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

// LOMBOK
@RequiredArgsConstructor
// SPRING
@Service
public class TicketService {

    private final TicketRepository repository;

    @Transactional
    public Optional < Ticket > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Ticket getOne ( UUID id ) {
        return repository.getReferenceById ( id );
    }

    @Transactional
    public Ticket create ( Ticket entity ) {
        return repository.save ( entity );
    }


    @Transactional
    public Ticket update ( Ticket entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( @NotNull final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Ticket > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }


}
