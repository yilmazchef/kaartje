package be.intecbrussel.data.service;

import be.intecbrussel.data.entity.Board;
import be.intecbrussel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID>, JpaSpecificationExecutor<Board> {

    List<Board> findByCreatedBy(@NotNull User createdBy);

    List<Board> findByCreatedBy_Id(@NotNull UUID createdById);
}