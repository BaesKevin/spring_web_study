package be.kevinbaes.goaltracker.repositories;

import be.kevinbaes.goaltracker.entities.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Long> {
}
