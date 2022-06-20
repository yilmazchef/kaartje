package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShareService {

    private final ShareRepository repository;

    public ShareService(ShareRepository repository) {
        this.repository = repository;
    }

    public Optional<Share> get(UUID id) {
        return repository.findById(id);
    }

    public Share update(Share entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Share> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Share> list(UUID ticketId, Pageable pageable) {
        return repository.findAllByTicketId(ticketId, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
