package be.intecbrussel.data.service;

import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.entity.Like;
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
public class LikeService {

    private final LikeRepository repository;
    private final LikeMapper mapper;

    @Transactional
    public Optional < Like > get ( UUID id ) {
        return repository.findById ( id );
    }

    @Transactional
    public Like create ( Like entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public Like update ( Like entity ) {
        return repository.save ( entity );
    }

    @Transactional
    public void delete ( UUID id ) {
        repository.deleteById ( id );
    }

    @Transactional
    public Page < Like > list ( Pageable pageable ) {
        return repository.findAll ( pageable );
    }

    @Transactional
    public Page < Like > list ( UUID ticketId, Pageable pageable ) {
        return repository.findAllByTicketId ( ticketId, pageable );
    }

    @Transactional
    public int count ( ) {
        return ( int ) repository.count ( );
    }

    // DTO SERVICES

    @Transactional
    public Optional < LikeDto > getDto ( @NotNull final UUID id ) {
        return repository
                .findById ( id )
                .map ( mapper :: likeToLikeDto );
    }

    @Transactional
    public LikeDto createDto ( @NotNull final LikeDto dto ) {
        return
                mapper.likeToLikeDto (
                        repository.save (
                                mapper.likeDtoToLike ( dto )
                        )
                );
    }

    @Transactional
    public LikeDto updateDto ( @NotNull final LikeDto dto ) {
        return
                mapper.likeToLikeDto (
                        repository.save (
                                mapper.likeDtoToLike ( dto )
                        )
                );
    }

    @Transactional
    public void deleteDto ( @NotNull final UUID id ) {

        repository.deleteById ( id );
    }

    @Transactional
    public Page < LikeDto > listDto ( @NotNull final Pageable pageable ) {

        return repository
                .findAll ( pageable )
                .map ( mapper :: likeToLikeDto );
    }

    @Transactional
    public Page < LikeDto > listByTicketIdDto ( @NotNull final UUID ticketId, @NotNull final Pageable pageable ) {
        return repository
                .findAllByTicket_Id ( ticketId, pageable )
                .map ( mapper :: likeToLikeDto );
    }

    @Transactional
    public int countDto ( ) {
        return ( int ) repository.count ( );
    }

}
