package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "car_models")
public class CarModel extends DomainBase {

    private Long id;
    private CarBrand carBrand;
    private String name;
    private Boolean active;

    public CarModel() {
        super();
    }

    public CarModel(Long id, CarBrand carBrand, String name) {
        this.id = id;
        this.carBrand = carBrand;
        this.name = name;
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

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}