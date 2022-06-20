package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    private final LikeRepository repository;

    public LikeService(LikeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Like> get(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Like update(Like entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public Page<Like> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Page<Like> list(UUID ticketId, Pageable pageable) {
        return repository.findAllByTicketId(ticketId, pageable);
    }

    @Transactional
    public int count() {
        return (int) repository.count();
    }

}
