package be.kevinbaes.goaltracker.services;

import be.kevinbaes.goaltracker.entities.Goal;
import be.kevinbaes.goaltracker.repositories.GoalRepository;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GoalService {
    private GoalRepository goalRepository;

    @Autowired
    public void setGoalRepository(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Iterable<Goal> findAll() {
        return goalRepository.findAll();
    }

    public Goal save(Goal goal) {
        return goalRepository.save(goal);
    }

    @PostConstruct
    public void initData() {
        if(goalRepository == null){
            throw new BeanInstantiationException(GoalService.class, "goalRepository not set");
        }
        goalRepository.save(new Goal("A new goal"));
    }
}
