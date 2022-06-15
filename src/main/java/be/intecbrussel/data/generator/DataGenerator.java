package be.intecbrussel.data.generator;

import be.intecbrussel.data.Role;
import be.intecbrussel.data.entity.Department;
import be.intecbrussel.data.entity.Response;
import be.intecbrussel.data.entity.Ticket;
import be.intecbrussel.data.entity.User;
import be.intecbrussel.data.service.DepartmentRepository;
import be.intecbrussel.data.service.ResponseRepository;
import be.intecbrussel.data.service.TicketRepository;
import be.intecbrussel.data.service.UserRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

        @Bean
        public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
                        TicketRepository ticketRepository, ResponseRepository resRepo,
                        DepartmentRepository deptRepo) {
                return args -> {
                        Logger logger = LoggerFactory.getLogger(getClass());
                        if (userRepository.count() != 0L) {
                                logger.info("Using existing database");
                                return;
                        }
                        int seed = 123;

                        logger.info("Generating demo data");

                        logger.info("... generating 2 User entities...");
                        final var user = new User()
                                        .setName("John Normal")
                                        .setUsername("user")
                                        .setHashedPassword(passwordEncoder.encode("user"))
                                        .setProfilePictureUrl(
                                                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                                        .setRoles(Collections.singleton(Role.USER));

                        userRepository.save(user);

                        final var admin = new User()
                                        .setName("Emma Powerful")
                                        .setUsername("admin")
                                        .setHashedPassword(passwordEncoder.encode("admin"))
                                        .setProfilePictureUrl(
                                                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                                        .setRoles(Set.of(Role.USER, Role.ADMIN));

                        userRepository.save(admin);

                        logger.info("... generating 100 Ticket entities...");

                        final var ticketGen = new ExampleDataGenerator<Ticket>(
                                        Ticket.class,
                                        LocalDateTime.of(2022, 6, 14, 0, 0, 0));

                        ticketGen.setData(Ticket::setSubject, DataType.SENTENCE);
                        ticketGen.setData(Ticket::setAttachment, DataType.BOOK_IMAGE_URL);
                        ticketGen.setData(Ticket::setContent, DataType.SENTENCE);
                        ticketGen.setData(Ticket::setCreatedBy, DataType.EMAIL);
                        ticketGen.setData(Ticket::setUpdatedBy, DataType.EMAIL);
                        ticketGen.setData(Ticket::setCreatedAt, DataType.DATETIME_LAST_30_DAYS);
                        ticketGen.setData(Ticket::setUpdatedAt, DataType.DATETIME_LAST_7_DAYS);
                        ticketGen.setData(Ticket::setStatus, DataType.WORD);
                        ticketGen.setData(Ticket::setIsActive, DataType.BOOLEAN_90_10);
                        
                        ticketRepository.saveAll(ticketGen.create(100, seed));

                        logger.info("... generating 100 Response entities...");

                        final var resGen = new ExampleDataGenerator<Response>(
                                        Response.class,
                                        LocalDateTime.of(2022, 6, 14, 0, 0, 0));
                        
                        resGen.setData(Response::setTicket, DataType.UUID);
                        resGen.setData(Response::setCreatedBy, DataType.EMAIL);
                        resGen.setData(Response::setCreatedAt, DataType.DATETIME_LAST_10_YEARS);
                        resGen.setData(Response::setUpdatedAt, DataType.DATETIME_LAST_7_DAYS);
                        resGen.setData(Response::setContent, DataType.SENTENCE);
                        resGen.setData(Response::setPriority, DataType.WORD);
                        resGen.setData(Response::setScore, DataType.NUMBER_UP_TO_100);
                        resGen.setData(Response::setTags, DataType.TWO_WORDS);
                        resGen.setData(Response::setIsActive, DataType.BOOLEAN_90_10);

                        resRepo.saveAll(resGen.create(100, seed));

                        logger.info("... generating 100 Department entities...");

                        final var deptGen = new ExampleDataGenerator<Department>(
                                        Department.class, LocalDateTime.of(2022, 6, 14, 0, 0, 0));

                        deptGen.setData(Department::setTitle, DataType.TWO_WORDS);
                        deptGen.setData(Department::setCreatedBy, DataType.EMAIL);
                        deptGen.setData(Department::setUpdatedBy, DataType.EMAIL);
                        deptGen.setData(Department::setAlias, DataType.TWO_WORDS);
                        deptGen.setData(Department::setContactEmail, DataType.EMAIL);
                        deptGen.setData(Department::setContactPhone, DataType.PHONE_NUMBER);

                        deptRepo.saveAll(deptGen.create(100, seed));

                        logger.info("Generated demo data");
                };
        }

}