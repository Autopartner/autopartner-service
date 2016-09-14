package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_brands")
public class CarBrand extends DomainBase {

    private Long id;
    private CarType carType;
    private String name;
    private List<CarModel> models;
    private Boolean active;

    public CarBrand() {
        super();
    }

    public CarBrand(Long id, CarType carType, String name, List<CarModel> models) {
        this.id = id;
        this.carType = carType;
        this.name = name;
        this.models = models;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_brands_seq")
    @SequenceGenerator(name = "c_brands_seq", sequenceName = "c_brands_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "c_type_id")
    @ManyToOne
    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    public List<CarModel> getModels() {
        return models;
    }

    public void setModels(List<CarModel> models) {
        this.models = models;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}