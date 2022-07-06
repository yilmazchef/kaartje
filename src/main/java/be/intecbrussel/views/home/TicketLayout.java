package be.intecbrussel.views.home;

import be.intecbrussel.data.dto.TicketDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class TicketLayout extends HorizontalLayout {

    @Getter
    private final Image image = new Image ( );

    @Getter
    private final VerticalLayout description = new VerticalLayout ( );

    @Getter
    private final HorizontalLayout header = new HorizontalLayout ( );

    @Getter
    private final Span name = new Span ( );

    @Getter
    private final Span date = new Span ( );

    @Getter
    private final Span post = new Span ( );

    @Getter
    private final HorizontalLayout actions = new HorizontalLayout ( );

    @Getter
    private final Button likeButton = new Button ( );

    @Getter
    private final Button commentButton = new Button ( );

    @Getter
    private final Button shareButton = new Button ( );


    public TicketLayout ( @NotEmpty final TicketDto ticketDto ) {

        addClassName ( "ticket-layout" );
        setSizeFull ( );

        addClassName ( "card" );
        setSpacing ( false );
        getThemeList ( ).add ( "spacing-s" );

        image.setSrc ( ticketDto.getCreatedBy ( ).getProfilePictureUrl ( ) );

        description.addClassName ( "description" );
        description.setSpacing ( false );
        description.setPadding ( false );

        header.addClassName ( "header" );
        header.setSpacing ( false );
        header.getThemeList ( ).add ( "spacing-s" );

        name.setText ( ticketDto.getCreatedBy ( ).toString () );
        name.addClassName ( "name" );

        date.setText ( ticketDto.getDate ( ) );
        date.addClassName ( "date" );
        header.add ( name, date );

        post.setText ( ticketDto.getPost ( ) );
        post.addClassName ( "post" );

        actions.addClassName ( "actions" );
        actions.setSpacing ( false );
        actions.getThemeList ( ).add ( "spacing-s" );

        final var likeIcon = VaadinIcon.HEART.create ( );
        likeButton.setIcon ( likeIcon );
        likeButton.addClassName ( "icon" );

        final var likes = new Span ( ticketDto.getLikes ( ) );
        likes.addClassName ( "likes" );

        final var commentIcon = VaadinIcon.COMMENT.create ( );
        commentButton.setIcon ( commentIcon );
        commentButton.addClassName ( "icon" );

        final var comments = new Span ( ticketDto.getComments ( ) );
        comments.addClassName ( "comments" );

        final var shareIcon = VaadinIcon.CONNECT.create ( );
        shareButton.setIcon ( shareIcon );
        shareButton.addClassName ( "icon" );

        final var shares = new Span ( ticketDto.getShares ( ) );
        shares.addClassName ( "shares" );

        actions.add ( likeButton, likes, commentButton, comments, shareButton, shares );

        description.add ( header, post, actions );
        add ( image, description );
    }

}
