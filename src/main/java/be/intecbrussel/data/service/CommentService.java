package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Optional<Comment> get(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Comment update(Comment entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Comment> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Comment> list(UUID ticketId, Pageable pageable) {
        return repository.findAllByTicket(ticketId, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
