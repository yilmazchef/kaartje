package be.intecbrussel.data.generator;

import be.intecbrussel.data.Role;
import be.intecbrussel.data.entity.User;
import be.intecbrussel.data.service.DepartmentRepository;
import be.intecbrussel.data.service.ResponseRepository;
import be.intecbrussel.data.service.TicketRepository;
import be.intecbrussel.data.service.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

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
                    .setFirstName("John")
                    .setLastName("Normal")
                    .setUsername("user")
                    .setHashedPassword(passwordEncoder.encode("user"))
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Collections.singleton(Role.USER));

            userRepository.save(user);

            final var admin = new User()
                    .setFirstName("Emma Powerful")
                    .setUsername("admin")
                    .setHashedPassword(passwordEncoder.encode("admin"))
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Set.of(Role.USER, Role.ADMIN));

            userRepository.save(admin);

            logger.info("... generating 100 Ticket entities...");

        };
    }

}