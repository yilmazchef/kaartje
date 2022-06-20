package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Category;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Category> get(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Category update(Category entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public Page<Category> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public int count() {
        return (int) repository.count();
    }

}
