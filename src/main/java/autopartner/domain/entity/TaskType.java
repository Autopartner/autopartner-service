package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_types")
public class TaskType extends DomainBase {

    private Long id;
    private String name;
    private List<Task> tasks;
    private Boolean active;

    public TaskType() {
        super();
    }

    public TaskType(Long id, String name, List<Task> tasks, Boolean active) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
        this.active = active;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_types_seq")
    @SequenceGenerator(name = "t_types_seq", sequenceName = "t_types_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}