package be.intecbrussel.views.home;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TicketLayout extends HorizontalLayout {

    public TicketLayout(TicketBinder ticketBinder) {
        addClassName("ticket-layout");
        setSizeFull();

        addClassName("card");
        setSpacing(false);
        getThemeList().add("spacing-s");

        final var image = new Image();
        image.setSrc(ticketBinder.getImage());

        final var description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        final var header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        final var name = new Span(ticketBinder.getName());
        name.addClassName("name");

        final var date = new Span(ticketBinder.getDate());
        date.addClassName("date");
        header.add(name, date);

        final var post = new Span(ticketBinder.getPost());
        post.addClassName("post");

        final var actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        final var likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("icon");
        final var likeButton = new Button(likeIcon, onClick -> {
            Notification.show(
                    "You liked " + ticketBinder.getName(),
                    3000,
                    Notification.Position.MIDDLE
            );
        });

        final var likes = new Span(ticketBinder.getLikes());
        likes.addClassName("likes");

        final var commentIcon = VaadinIcon.COMMENT.create();
        commentIcon.addClassName("icon");
        final var commentButton = new Button(commentIcon, onClick -> {
            Notification.show(
                    "You commented " + ticketBinder.getName(),
                    3000,
                    Notification.Position.MIDDLE
            );
        });

        final var comments = new Span(ticketBinder.getComments());
        comments.addClassName("comments");

        final var shareIcon = VaadinIcon.CONNECT.create();
        shareIcon.addClassName("icon");
        final var shareButton = new Button(shareIcon, onClick -> {
            Notification.show(
                    "You shared " + ticketBinder.getName(),
                    3000,
                    Notification.Position.MIDDLE
            );
        });

        final var shares = new Span(ticketBinder.getShares());
        shares.addClassName("shares");

        actions.add(likeButton, likes, commentButton, comments, shareButton, shares);

        description.add(header, post, actions);
        add(image, description);
    }

}
