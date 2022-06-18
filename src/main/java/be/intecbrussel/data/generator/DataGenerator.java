package be.intecbrussel.data.generator;

import be.intecbrussel.data.Role;
import be.intecbrussel.data.entity.User;
import be.intecbrussel.data.service.DepartmentRepository;
import be.intecbrussel.data.service.ResponseRepository;
import be.intecbrussel.data.service.TicketRepository;
import be.intecbrussel.data.service.UserRepository;
import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

@SpringComponent
@Slf4j
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                      TicketRepository ticketRepository, ResponseRepository resRepo,
                                      DepartmentRepository deptRepo) {

        final var faker = new Faker(Locale.forLanguageTag("be-NL"));

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            final var userPwd = faker.internet().password();
            final var user = new User()
                    .setFirstName(faker.name().firstName())
                    .setLastName(faker.name().lastName())
                    .setUsername(faker.name().username())
                    .setPhone(faker.phoneNumber().cellPhone())
                    .setHashedPassword(passwordEncoder.encode(userPwd))
                    .setIsDeleted(false)
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Collections.singleton(Role.USER));

            final var savedUser = userRepository.save(user);
            log.info(MessageFormat.format("Saved user {0} with pwd: {1} and id {2}", savedUser.getUsername(), userPwd, savedUser.getId()));

            final var adminPwd = faker.internet().password();
            final var admin = new User()
                    .setFirstName(faker.name().firstName())
                    .setLastName(faker.name().lastName())
                    .setUsername(faker.name().username())
                    .setPhone(faker.phoneNumber().cellPhone())
                    .setHashedPassword(passwordEncoder.encode(adminPwd))
                    .setIsDeleted(false)
                    .setProfilePictureUrl(
                            "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80")
                    .setRoles(Set.of(Role.USER, Role.ADMIN));

            final var savedAdmin = userRepository.save(admin);
            log.info(MessageFormat.format("Saved admin {0} with pwd: {1} and id {2}", savedAdmin.getUsername(), adminPwd, savedAdmin.getId()));

            log.info(MessageFormat.format("{0} user entities are created...", 2));
        };
    }

}