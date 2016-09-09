package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cl_clients")
public class Client extends DomainBase {

    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String phone;
    private String email;
    private Date dateCreated;
    private Double discountService;
    private Double discountMaterial;
    private Type type;
    private String note;
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

    public Client() {
        super();
    }

    public Client(String firstName, String lastName, String companyName, String address, String phone, String email,
                  Date dateCreated, Double discountService, Double discountMaterial, Type type, String note, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.dateCreated = dateCreated;
        this.discountService = discountService;
        this.discountMaterial = discountMaterial;
        this.type = type;
        this.note = note;
        this.isActive = isActive;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "discount_service")
    public Double getDiscountService() {
        return discountService;
    }

    public void setDiscountService(Double discountService) {
        this.discountService = discountService;
    }

    @Column(name = "discount_material")
    public Double getDiscountMaterial() {
        return discountMaterial;
    }

    public void setDiscountMaterial(Double discountMaterial) {
        this.discountMaterial = discountMaterial;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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



    public enum Type {
        PERSON,
        LEGAL
    }
}