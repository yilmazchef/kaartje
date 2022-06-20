package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> get(UUID id) {
        return repository.findById(id);
    }

    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Page<User> list() {
        return list(PageRequest.of(0, 25));
    }

    @Transactional
    public User update(User entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
