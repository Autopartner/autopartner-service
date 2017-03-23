package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cars")
public class Car extends DomainBase {

    private Long id;
    private Client client;
    private CarModel carModel;
    private String regNumber;
    private String vinCode;
    private String passportNumber;
    private String notes;
    private Double mileage;
    private Date regDate;
    private Boolean active;

    public Car() {
        super();
    }

    public Car(Client client, CarModel carModel,
               String regNumber, String vinCode, String passportNumber,
               String notes, Double mileage, Date regDate) {
        this.client = client;
        this.carModel = carModel;
        this.regNumber = regNumber;
        this.vinCode = vinCode;
        this.passportNumber = passportNumber;
        this.notes = notes;
        this.mileage = mileage;
        this.regDate = regDate;
    }

    @PrePersist
    protected void onCreate() {
        regDate = new Date();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_seq")
    @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "c_model_id")
    @ManyToOne
    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    @JoinColumn(name = "client_id")
    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "reg_number")
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @Column(name = "vin_code")
    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    @Column(name = "passport_number")
    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "mileage")
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    @Column(name = "reg_date")
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}