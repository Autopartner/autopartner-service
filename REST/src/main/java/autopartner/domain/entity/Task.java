package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task extends DomainBase {

    private Long id;
    private TaskType taskType;
    private String name;
    private Boolean active;

    public Task() {
        super();
    }

    public Task(Long id, TaskType taskType, String name, Boolean active) {
        this.id = id;
        this.taskType = taskType;
        this.name = name;
        this.active = active;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "t_type_id")
    @ManyToOne
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}