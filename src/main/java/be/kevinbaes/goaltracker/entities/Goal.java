package be.kevinbaes.goaltracker.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    private Date dateCompleted;

    public long getId() {
        return id;
    }

    public Goal() {
        this("");
    }

    public Goal(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
