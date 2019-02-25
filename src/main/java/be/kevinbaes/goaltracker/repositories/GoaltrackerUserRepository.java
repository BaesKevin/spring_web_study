package be.kevinbaes.goaltracker.repositories;

import be.kevinbaes.goaltracker.entities.GoaltrackerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoaltrackerUserRepository extends JpaRepository<GoaltrackerUser, Long> {
    GoaltrackerUser findByUsername(String username);
}
