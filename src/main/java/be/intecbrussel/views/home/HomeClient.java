package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.CommentDto;
import be.intecbrussel.data.dto.LikeDto;
import be.intecbrussel.data.dto.ShareDto;
import be.intecbrussel.data.dto.TicketDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Service
public class HomeClient implements Serializable, IHomeApi {

    @Value ( "${server.port}" )
    private String serverPort;

    private final String baseUrl = "http://localhost:" + serverPort + HomeEndpoints.HOME_BASE_END_POINT;

    /**
     * Fetches the total number of items available through the REST API
     */
    public int fetchCount ( ) {

        System.out.println ( "fetching count..." );

        // We use a local provider for this bigger data set.
        // The API has two methods, 'data' and 'count'.
        final String url = baseUrl + HomeEndpoints.GET_TICKETS_COUNT_END_POINT;

        final RequestHeadersSpec < ? > spec = WebClient.create ( ).get ( ).uri ( url );
        final Integer response = spec.retrieve ( ).toEntity ( Integer.class ).block ( ).getBody ( );

        System.out.println ( "...count is " + response );
        return response;

    }

    public Long getLikesCount ( Long id ) {

        String url = String.format ( "http://localhost:" + serverPort + "api/home/likes/" + id );

        final RequestHeadersSpec < ? > spec = WebClient.create ( ).get ( ).uri ( url );
        final Long response = spec.retrieve ( ).toEntity ( Long.class ).block ( ).getBody ( );

        return response;
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @Override
    public List < TicketDto > getTickets ( @NotNull final int page, @NotNull final int size ) {

        System.out.println ( String.format ( "Fetching partial data set %d through %d...", size, page + size ) );

        // We use a local provider for this bigger data set.
        // The API has two methods, 'data' and 'count'.

        // Other than that, this method is similar to #getAllComments().
        final String url = String.format ( "http://localhost:" + serverPort + "/api/home/tickets/{ticket_id}/likes?page=%d&size=%d", page, size );

        final RequestHeadersSpec < ? > spec = WebClient.create ( ).get ( ).uri ( url );
        final List < TicketDto > tickets = spec.retrieve ( ).toEntityList ( TicketDto.class ).block ( ).getBody ( );

        System.out.println ( String.format ( "...received %d items.", tickets.size ( ) ) );

        return tickets;
    }

    /**
     * @param page
     * @param size
     * @param ticketId
     * @return
     */
    @Override
    public List < LikeDto > getLikes ( @NotNull final int page, @NotNull final int size, @NotNull final UUID ticketId ) {
        return null;
    }

    /**
     * @param ticketId
     * @return
     */
    @Override
    public long getLikesCount ( @NotNull final UUID ticketId ) {
        return 0;
    }

    /**
     * @param page
     * @param size
     * @param ticketId
     * @return
     */
    @Override
    public List < ShareDto > getShares ( @NotNull final int page, @NotNull final int size, @NotNull final UUID ticketId ) {
        return null;
    }

    /**
     * @param ticketId
     * @return
     */
    @Override
    public long getSharesCount ( UUID ticketId ) {
        return 0;
    }

    /**
     * @param page
     * @param size
     * @param ticketId
     * @return
     */
    @Override
    public List < CommentDto > getComments ( @NotNull final int page, @NotNull final int size, @NotNull final UUID ticketId ) {
        return null;
    }

    /**
     * @param ticketId
     * @return
     */
    @Override
    public long getCommentsCount ( @NotNull final UUID ticketId ) {
        return 0;
    }

    /**
     * @param ticketId
     * @param commentDto
     * @return
     */
    @Override
    public CommentDto postComment ( @NotNull final UUID ticketId, @NotNull final CommentDto commentDto ) {
        return null;
    }

    /**
     * @param ticketId
     * @param commentId
     * @param commentDto
     * @return
     */
    @Override
    public CommentDto updateComment ( UUID ticketId, UUID commentId, CommentDto commentDto ) {
        return null;
    }

    /**
     * @param ticketId
     * @param likeDto
     * @return
     */
    @Override
    public LikeDto likeTicket ( @NotNull UUID ticketId, LikeDto likeDto ) {
        return null;
    }

    /**
     * @param ticketId
     * @return
     */
    @Override
    public LikeDto likeTicket ( @NotNull UUID ticketId ) {
        return null;
    }

    /**
     * @param ticketId
     * @param likeId
     * @return
     */
    @Override
    public LikeDto deleteLike ( UUID ticketId, UUID likeId ) {
        return null;
    }
}