package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_types")
public class CarType extends DomainBase {

    private Long id;
    private String name;
    private List<CarBrand> brands;

    public CarType() {
        super();
    }

    public CarType(Long id, String name, List<CarBrand> brands) {
        this.id = id;
        this.name = name;
        this.brands = brands;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_types_seq")
    @SequenceGenerator(name = "c_types_seq", sequenceName = "c_types_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "carType", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    public List<CarBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<CarBrand> brands) {
        this.brands = brands;
    }
}