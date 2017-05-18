package autopartner.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_tasks")
public class OrderTask {

    private Long id;
    private Order order;
    private Task task;
    private Integer count;
    private Double price;
    private Double discount;
    private String note;
    private Boolean isActive;

    public OrderTask() { super(); }

    public OrderTask(Long id, Task task, Integer count, Double price, Double discount, String note, Boolean isActive) {
        this.id = id;
        this.task = task;
        this.count = count;
        this.price = price;
        this.discount = discount;
        this.note = note;
        this.isActive = isActive;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_tasks_seq")
    @SequenceGenerator(name = "order_tasks_seq", sequenceName = "order_tasks_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "order_id")
    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @JoinColumn(name = "task_id")
    @ManyToOne
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "discount")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
