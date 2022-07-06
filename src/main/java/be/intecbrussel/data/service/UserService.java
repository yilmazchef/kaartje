package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.UserDto;
import be.intecbrussel.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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


    // DTO SERVICES

    @Transactional
    public Optional < UserDto > getDto ( @NotNull final UUID id ) {

        return repository
                .findById ( id )
                .map ( mapper :: userToUserDto );
    }

    @Transactional
    public Optional < UserDto > findByUsernameDto ( @NotEmpty final String username ) {
        return repository
                .findByUsername ( username )
                .map ( mapper :: userToUserDto );
    }

    @Transactional
    public UserDto getByUsernameDto ( @NotEmpty final String username ) {
        return mapper.userToUserDto (
                repository
                        .getByUsername ( username )
        );
    }

    @Transactional
    public Page < UserDto > listDto ( ) {
        return listDto ( PageRequest.of ( 0, 25 ) );
    }

    @Transactional
    public UserDto updateDto ( @NotNull final UserDto dto ) {

        return
                mapper.userToUserDto (
                        repository.save (
                                mapper.userDtoToUser ( dto )
                        )
                );
    }

    @Transactional
    public void deleteDto ( @NotNull final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < UserDto > listDto ( @NotNull final Pageable pageable ) {

        return repository
                .findAll ( pageable )
                .map ( mapper :: userToUserDto );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

}
