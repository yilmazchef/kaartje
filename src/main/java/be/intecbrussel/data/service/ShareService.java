package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.entity.Share;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// LOMBOK
@RequiredArgsConstructor
// SPRING
@Service
public class ShareService {

    private final ShareRepository repository;
    private final ShareMapper mapper;

    @Transactional
    public Optional < Share > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Share create ( Share entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public Share update ( Share entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Share > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public List < Share > list ( ) {
        return repository.findAll ( );
    }

    @Transactional
    public Page < Share > list ( UUID ticketId, Pageable pageable ) {
        return repository.findAllByTicket ( ticketId, pageable );
    }

    @Transactional
    public List < Share > list ( UUID ticketId ) {
        return repository.findAllByTicket ( ticketId );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }

    // DTO SERVICES

    @Transactional
    public Optional < ShareDto > getDto ( @NotNull final UUID id ) {

        return repository
                .findById ( id )
                .map ( mapper :: shareToShareDto );
    }

    @Transactional
    public ShareDto createDto ( @NotNull final ShareDto dto ) {
        return mapper.shareToShareDto (
                repository.save (
                        mapper.shareDtoToShare ( dto )
                )
        );
    }

    @Transactional
    public ShareDto updateDto ( @NotNull final ShareDto dto ) {

        return mapper.shareToShareDto (
                repository.save (
                        mapper.shareDtoToShare ( dto )
                )
        );
    }

    @Transactional
    public void deleteDto ( @NotNull final UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < ShareDto > listDto ( @NotNull final Pageable pageable ) {

        return repository
                .findAll ( pageable )
                .map ( mapper :: shareToShareDto );
    }

    @Transactional
    public List < ShareDto > listDto ( ) {

        return repository
                .findAll ( )
                .stream ( )
                .map ( mapper :: shareToShareDto )
                .collect ( Collectors.toUnmodifiableList ( ) );
    }

    @Transactional
    public Page < ShareDto > listByTicketIdDto ( @NotNull final UUID ticketId, @NotNull final Pageable pageable ) {
        return repository
                .findAllByTicket ( ticketId, pageable )
                .map ( mapper :: shareToShareDto );
    }

    @Transactional
    public List < ShareDto > listDto ( @NotNull final UUID ticketId ) {
        return repository
                .findAllByTicket ( ticketId )
                .stream ( )
                .map ( mapper :: shareToShareDto )
                .collect ( Collectors.toUnmodifiableList ( ) );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

    public int countByTicketDto ( @NotNull final UUID ticketId ) {
        return repository.countByTicket ( ticketId );
    }

}
