package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

// LOMBOK
@RequiredArgsConstructor
// SPRING
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public Optional < User > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Optional < User > findByUsername ( String username ) {
        return repository.findByUsername ( username );
    }

    @Transactional
    public User getByUsername ( String username ) {
        return repository.getByUsername ( username );
    }

    @Transactional
    public Page < User > list ( ) {
        return list ( PageRequest.of ( 0, 25 ) );
    }

    @Transactional
    public User update ( User entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < User > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }

}
