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
            TicketRepository ticketRepository, ResponseRepository responseRepository,
            DepartmentRepository departmentRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("Emma Powerful");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            userRepository.save(admin);
            logger.info("... generating 100 Ticket entities...");
            ExampleDataGenerator<Ticket> ticketRepositoryGenerator = new ExampleDataGenerator<>(Ticket.class,
                    LocalDateTime.of(2022, 6, 14, 0, 0, 0));
            ticketRepositoryGenerator.setData(Ticket::setSubject, DataType.SENTENCE);
            ticketRepositoryGenerator.setData(Ticket::setAttachment, DataType.BOOK_IMAGE_URL);
            ticketRepositoryGenerator.setData(Ticket::setContent, DataType.SENTENCE);
            ticketRepositoryGenerator.setData(Ticket::setCreatedBy, DataType.EMAIL);
            ticketRepositoryGenerator.setData(Ticket::setUpdatedBy, DataType.EMAIL);
            ticketRepositoryGenerator.setData(Ticket::setCreatedAt, DataType.DATETIME_LAST_30_DAYS);
            ticketRepositoryGenerator.setData(Ticket::setUpdatedAt, DataType.DATETIME_LAST_7_DAYS);
            ticketRepositoryGenerator.setData(Ticket::setStatus, DataType.WORD);
            ticketRepositoryGenerator.setData(Ticket::setIsActive, DataType.BOOLEAN_90_10);
            ticketRepository.saveAll(ticketRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Response entities...");
            ExampleDataGenerator<Response> responseRepositoryGenerator = new ExampleDataGenerator<>(Response.class,
                    LocalDateTime.of(2022, 6, 14, 0, 0, 0));
            responseRepositoryGenerator.setData(Response::setTicket, DataType.UUID);
            responseRepositoryGenerator.setData(Response::setCreatedBy, DataType.EMAIL);
            responseRepositoryGenerator.setData(Response::setCreatedAt, DataType.DATETIME_LAST_10_YEARS);
            responseRepositoryGenerator.setData(Response::setUpdatedAt, DataType.DATETIME_LAST_7_DAYS);
            responseRepositoryGenerator.setData(Response::setContent, DataType.SENTENCE);
            responseRepositoryGenerator.setData(Response::setPriority, DataType.WORD);
            responseRepositoryGenerator.setData(Response::setScore, DataType.NUMBER_UP_TO_100);
            responseRepositoryGenerator.setData(Response::setTags, DataType.TWO_WORDS);
            responseRepositoryGenerator.setData(Response::setIsActive, DataType.BOOLEAN_90_10);
            responseRepository.saveAll(responseRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Department entities...");
            ExampleDataGenerator<Department> departmentRepositoryGenerator = new ExampleDataGenerator<>(
                    Department.class, LocalDateTime.of(2022, 6, 14, 0, 0, 0));
            departmentRepositoryGenerator.setData(Department::setTitle, DataType.TWO_WORDS);
            departmentRepositoryGenerator.setData(Department::setCreatedBy, DataType.EMAIL);
            departmentRepositoryGenerator.setData(Department::setUpdatedBy, DataType.EMAIL);
            departmentRepositoryGenerator.setData(Department::setAlias, DataType.TWO_WORDS);
            departmentRepositoryGenerator.setData(Department::setContactEmail, DataType.EMAIL);
            departmentRepositoryGenerator.setData(Department::setContactPhone, DataType.PHONE_NUMBER);
            departmentRepository.saveAll(departmentRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}