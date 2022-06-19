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
            if(oUser.isEmpty()) {
                userContainer.newUserRequest = userRepository.save(user);
            } else {
                userContainer.newUserRequest = oUser.get();
                userContainer.newUserRequest.setHashedPassword(passwordEncoder.encode(userPwd));
            }

            final var savedUser = userRepository.save(userContainer.newUserRequest);
            log.info(MessageFormat.format("Saved user {0} with pwd: {1} and id {2}", savedUser.getUsername(), userPwd, savedUser.getId()));

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
            if(oAdmin.isEmpty()) {
                adminContainer.newAdminRequest = userRepository.save(admin);
            } else {
                adminContainer.newAdminRequest = oAdmin.get();
                adminContainer.newAdminRequest.setHashedPassword(passwordEncoder.encode(adminPwd));
            }


            final var savedAdmin = userRepository.save(admin);
            log.info(MessageFormat.format("Saved admin {0} with pwd: {1} and id {2}", savedAdmin.getUsername(), adminPwd, savedAdmin.getId()));

            log.info(MessageFormat.format("{0} user entities are created...", 2));
        };
    }

}