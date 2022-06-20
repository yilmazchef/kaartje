package be.intecbrussel.data.generator;

import be.intecbrussel.data.Role;
import be.intecbrussel.data.entity.*;
import be.intecbrussel.data.service.*;
import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringComponent
@Slf4j
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                      TicketRepository ticketRepository, CommentRepository commentRepository,
                                      CategoryRepository categoryRepository, BoardRepository boardRepository,
                                      ShareRepository shareRepository, LikeRepository likeRepository) {

        final var faker = new Faker(Locale.forLanguageTag("be-NL"));

        return args -> {

            log.info("Seeding database with users");

            final var userEmail = faker.internet().emailAddress();
            final var userPwd = faker.internet().password();
            final var user = new User()
                    .setFirstName(faker.name().firstName())
                    .setLastName(faker.name().lastName())
                    .setUsername(userEmail)
                    .setPhone(faker.phoneNumber().cellPhone())
                    .setHashedPassword(passwordEncoder.encode(userPwd))
                    .setIsDeleted(false)
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Collections.singleton(Role.USER));

            final var userContainer = new Object() {
                User newUserRequest = null;
            };

            final var oUser = userRepository.findByUsername(userEmail);
            if (oUser.isEmpty()) {
                userContainer.newUserRequest = userRepository.save(user);
            } else {
                userContainer.newUserRequest = oUser.get();
                userContainer.newUserRequest.setHashedPassword(passwordEncoder.encode(userPwd));
            }
            log.info(MessageFormat.format("Saved user {0} with pwd: {1} and id {2}",
                    userContainer.newUserRequest.getUsername(),
                    userPwd,
                    userContainer.newUserRequest.getId()));

            final var adminEmail = faker.internet().emailAddress();
            final var adminPwd = faker.internet().password();
            final var admin = new User()
                    .setFirstName(faker.name().firstName())
                    .setLastName(faker.name().lastName())
                    .setUsername(adminEmail)
                    .setPhone(faker.phoneNumber().cellPhone())
                    .setHashedPassword(passwordEncoder.encode(adminPwd))
                    .setIsDeleted(false)
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Set.of(Role.USER, Role.ADMIN));

            final var adminContainer = new Object() {
                User newAdminRequest = null;
            };

            final var oAdmin = userRepository.findByUsername(adminEmail);
            if (oAdmin.isEmpty()) {
                adminContainer.newAdminRequest = userRepository.save(admin);
            } else {
                adminContainer.newAdminRequest = oAdmin.get();
                adminContainer.newAdminRequest.setHashedPassword(passwordEncoder.encode(adminPwd));
                userRepository.save(adminContainer.newAdminRequest);
            }

            log.info(MessageFormat.format("Saved admin {0} with pwd: {1} and id {2}",
                    adminContainer.newAdminRequest.getUsername(),
                    adminPwd,
                    adminContainer.newAdminRequest.getId()));

            IntStream
                    .rangeClosed(1, 10)
                    .mapToObj(index -> {
                        final var studentEmail = faker.internet().emailAddress();
                        final var studentPwd = faker.internet().password();
                        final var student = new User()
                                .setFirstName(faker.name().firstName())
                                .setLastName(faker.name().lastName())
                                .setUsername(studentEmail)
                                .setPhone(faker.phoneNumber().cellPhone())
                                .setHashedPassword(passwordEncoder.encode(studentPwd))
                                .setIsDeleted(false)
                                .setProfilePictureUrl(
                                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                                .setRoles(Set.of(Role.STUDENT));

                        final var studentContainer = new Object() {
                            User newStudentRequest = null;
                        };

                        final var oStudent = userRepository.findByUsername(studentEmail);
                        if (oStudent.isEmpty()) {
                            studentContainer.newStudentRequest = userRepository.save(student);
                        } else {
                            studentContainer.newStudentRequest = oStudent.get();
                            studentContainer.newStudentRequest.setHashedPassword(passwordEncoder.encode(studentPwd));
                        }

                        log.info(MessageFormat.format("Saved student {0} with pwd: {1} and id {2}",
                                studentContainer.newStudentRequest.getUsername(), studentPwd,
                                studentContainer.newStudentRequest.getId()));

                        return studentContainer.newStudentRequest;
                    })
                    .forEachOrdered(student -> {
                        final var boardContainer = new Object() {
                            final Board newBoardRequest = new Board()
                                    .setTitle(faker.lorem().sentence())
                                    .setDescription(faker.lorem().paragraph())
                                    .setCreatedBy(adminContainer.newAdminRequest)
                                    .setAttachment(faker.internet().image())
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(7, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setUpdatedBy(adminContainer.newAdminRequest)
                                    .setUpdatedAt(LocalDateTime.ofInstant(faker.date().past(1, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setBackground(faker.internet().image())
                                    .setStatus("DRAFT")
                                    .setColor(faker.color().hex())
                                    .setContent(faker.lorem().paragraph());
                        };

                        boardRepository.save(boardContainer.newBoardRequest);

                        log.info(
                                MessageFormat.format("Saved board {0} with id {1}",
                                        boardContainer.newBoardRequest.getTitle(),
                                        boardContainer.newBoardRequest.getId())
                        );

                        final var ticketContainer = new Object() {
                            final Ticket newTicketRequest = new Ticket()
                                    .setSubject(faker.lorem().sentence())
                                    .setContent(faker.lorem().paragraph())
                                    .setTags(String.join(",", faker.lorem().words(5)))
                                    .setIsDeleted(false)
                                    .setCreatedBy(student)
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(7, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setAttachment("https://" + faker.internet().url())
                                    .setStatus("TODO")
                                    .setAssignedTo(adminContainer.newAdminRequest)
                                    .setBoard(boardContainer.newBoardRequest)
                                    .setPriority(faker.random().nextInt(1, 5))
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(2, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setUpdatedBy(student)
                                    .setLikes(faker.random().nextInt(0, 1000));
                        };

                        ticketRepository.save(ticketContainer.newTicketRequest);

                        log.info(
                                MessageFormat.format("Saved ticket {0} with id {1}",
                                        ticketContainer.newTicketRequest.getSubject(),
                                        ticketContainer.newTicketRequest.getId())
                        );

                        final var commentContainer = new Object() {
                            final Comment newCommentRequest = new Comment()
                                    .setContent(faker.lorem().paragraph())
                                    .setIsDeleted(false)
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(5, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setCreatedBy(student)
                                    .setPriority(faker.random().nextInt(1, 5))
                                    .setUpdatedAt(LocalDateTime.ofInstant(faker.date().past(3, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setTags(String.join(",", faker.lorem().words(5)))
                                    .setTicket(ticketContainer.newTicketRequest);
                        };

                        commentRepository.save(commentContainer.newCommentRequest);

                        log.info(
                                MessageFormat.format("Saved comment {0} with id {1}",
                                        commentContainer.newCommentRequest.getContent(),
                                        commentContainer.newCommentRequest.getId())
                        );

                        final var likeContainer = new Object() {
                            final Like newLikeRequest = new Like()
                                    .setIsDeleted(false)
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(4, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setCreatedBy(student)
                                    .setUpdatedAt(LocalDateTime.ofInstant(faker.date().past(2, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setTicket(ticketContainer.newTicketRequest);
                        };

                        likeRepository.save(likeContainer.newLikeRequest);

                        log.info(
                                MessageFormat.format("Saved like from the user {0} for the ticket {1} with id {2}",
                                        likeContainer.newLikeRequest.getCreatedBy().getUsername(),
                                        likeContainer.newLikeRequest.getTicket().getId(),
                                        likeContainer.newLikeRequest.getId())
                        );

                        final var shareContainer = new Object() {
                            final Share newShareRequest = new Share()
                                    .setIsDeleted(false)
                                    .setCreatedAt(LocalDateTime.ofInstant(faker.date().past(4, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setCreatedBy(student)
                                    .setSharedOn(faker.internet().url())
                                    .setViewsCount(faker.random().nextInt(0, 1_000_000))
                                    .setUpdatedAt(LocalDateTime.ofInstant(faker.date().past(2, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                    .setTicket(ticketContainer.newTicketRequest);
                        };

                        shareRepository.save(shareContainer.newShareRequest);

                        log.info(
                                MessageFormat.format("Saved share from the user {0} for the ticket {1} with id {2}",
                                        shareContainer.newShareRequest.getCreatedBy().getUsername(),
                                        shareContainer.newShareRequest.getTicket().getId(),
                                        shareContainer.newShareRequest.getId())
                        );

                        final var categoryContainer = new Object() {
                            final Category newCategoryRequest = new Category()
                                    .setTitle(faker.lorem().sentence())
                                    .setAlias(faker.lorem().word())
                                    .setIsDeleted(false)
                                    .setCreatedBy(adminContainer.newAdminRequest)
                                    .setUpdatedBy(adminContainer.newAdminRequest);
                        };

                        categoryRepository.save(categoryContainer.newCategoryRequest);

                        log.info(
                                MessageFormat.format("Saved category {0} with id {1}",
                                        categoryContainer.newCategoryRequest.getTitle(),
                                        categoryContainer.newCategoryRequest.getId())
                        );


                    });

            log.info(MessageFormat.format("{0} user entities are created...", 2));


        };
    }

}