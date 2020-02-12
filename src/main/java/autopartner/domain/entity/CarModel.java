package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_models")
public class CarModel extends DomainBase {

    private Long id;
    private CarBrand carBrand;
    private String name;
    private List<Car> cars;
    private Boolean active;

    public CarModel() {
        super();
    }

    public CarModel(CarBrand carBrand, String name, List<Car> cars) {
        this.carBrand = carBrand;
        this.name = name;
        this.cars = cars;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_models_seq")
    @SequenceGenerator(name = "c_models_seq", sequenceName = "c_models_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "c_brand_id")
    @ManyToOne
    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonIgnore
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}