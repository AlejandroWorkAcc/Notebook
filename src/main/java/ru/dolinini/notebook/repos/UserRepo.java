package ru.dolinini.notebook.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolinini.notebook.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByFirstname (String firstname);
    boolean existsByFirstname(String firstname);
}
