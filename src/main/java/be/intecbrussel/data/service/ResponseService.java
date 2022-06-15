package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Response;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    private final ResponseRepository repository;

    public ResponseService(ResponseRepository repository) {
        this.repository = repository;
    }

    public Optional<Response> get(UUID id) {
        return repository.findById(id);
    }

    public Response update(Response entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Response> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
