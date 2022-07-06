package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.CategoryDto;
import be.intecbrussel.data.entity.Category;
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
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Transactional
    public Optional < Category > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Category update ( Category entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Category > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }


    // DTO SERVICES

    @Transactional
    public Optional < CategoryDto > getDto ( @NotNull final UUID id ) {

        return repository
                .findById ( id )
                .map ( mapper :: categoryToCategoryDto );
    }

    @Transactional
    public CategoryDto updateDto ( CategoryDto dto ) {

        return mapper.categoryToCategoryDto (
                repository.save (
                        mapper.categoryDtoToCategory ( dto )
                ) );
    }

    @Transactional
    public void deleteDto ( @NotNull final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < CategoryDto > listDto ( @NotNull final Pageable pageable ) {

        return repository
                .findAll ( pageable )
                .map ( mapper :: categoryToCategoryDto );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

}
