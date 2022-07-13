package be.intecbrussel.data.service;

import be.intecbrussel.data.Role;
import be.intecbrussel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository < User, UUID >, JpaSpecificationExecutor < User > {

    Optional < User > findByUsername ( @NotEmpty @Email final String username );

    User getByUsername ( @NotEmpty @Email final String username );

    int countByRole(@NotNull final Role role);

}