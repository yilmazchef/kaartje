package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareService {

    private final ShareRepository repository;

    public ShareService(ShareRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Share> get(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Share create(Share entity) {
        return repository.save(entity);
    }

    @Transactional
    public Share update(Share entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public Page<Share> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public List<Share> list() {
        return repository.findAll();
    }

    @Transactional
    public Page<Share> list(UUID ticketId, Pageable pageable) {
        return repository.findAllByTicket(ticketId, pageable);
    }

    @Transactional
    public List<Share> list(UUID ticketId) {
        return repository.findAllByTicket(ticketId);
    }

    @Transactional
    public int count() {
        return (int) repository.count();
    }

}
