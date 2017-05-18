package autopartner.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    private Long id;
    private String orderNumber;
    private Car car;
    private Double mileage;
    private PaymentType paymentType;
    private Status status;
    private String note;
    private Date dateCreated;
    private Date dateUpdated;
    private Boolean isActive;
    private List<OrderTask> orderTasks;
    private List<OrderMaterial> orderMaterials;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

    @PreUpdate
    public void onPreUpdate() { dateUpdated = new Date(); }

    public Order() { super();}

    public Order(Long id, String orderNumber, Car car, Double mileage, PaymentType paymentType, Status status, String note,
                 Date dateCreated, Date dateUpdated, Boolean isActive, List<OrderTask> orderTasks, List<OrderMaterial> orderMaterials) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.car = car;
        this.mileage = mileage;
        this.paymentType = paymentType;
        this.status = status;
        this.note = note;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.isActive = isActive;
        this.orderTasks = orderTasks;
        this.orderMaterials = orderMaterials;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_number")
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JoinColumn(name = "car_id")
    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Column(name = "mileage")
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "date_updated")
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonIgnore
    public List<OrderTask> getOrderTasks() {
        return orderTasks;
    }

    public void setOrderTasks(List<OrderTask> orderTasks) {
        this.orderTasks = orderTasks;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonIgnore
    public List<OrderMaterial> getOrderMaterials() {
        return orderMaterials;
    }

    public void setOrderMaterials(List<OrderMaterial> orderMaterials) {
        this.orderMaterials = orderMaterials;
    }

    private enum PaymentType {
        CASH,
        CARD
    }

    private enum Status {
        OPENED,
        DONE,
        CLOSED,
        CANCELLED
    }

}
