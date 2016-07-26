package autopartner.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "APP_USER")
public class User {

    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    @Basic(optional = false)
    @Column(name = "PASSWORD_HASH")
    private String password;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MID_NAME")
    private String midName;

    @NotEmpty
    @Column(name = "NUMBER", unique = true)
    private String number;

    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Pattern(regexp="(^$|[0-9]{10})", message = "must be numeric")
    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NOTE")
    private String note;

    @NotNull
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, String midName, String number,
                Date createDate, String phone, String note, Boolean isActive, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.number = number;
        this.createDate = createDate;
        this.phone = phone;
        this.note = note;
        this.isActive = isActive;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        USER,
        ADMIN,
        MANAGER
    }
}