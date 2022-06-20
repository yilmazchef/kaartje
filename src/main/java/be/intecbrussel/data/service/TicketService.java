package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository repository;

    public TicketService(final TicketRepository repository) {
        this.repository = repository;
    }

    public Optional<Ticket> get(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Ticket update(Ticket entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Ticket> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
