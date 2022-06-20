package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Comment update(Comment entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Comment> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Comment> list(UUID ticketId, Pageable pageable) {
        return repository.findAllByTicketId(ticketId, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
