package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "material_types")
public class MaterialType extends DomainBase {

    private Long id;
    private String name;
    private List<Material> materials;
    private Boolean active;

    public MaterialType() {
        super();
    }

    public MaterialType(Long id, String name, List<Material> materials, Boolean active) {
        this.id = id;
        this.name = name;
        this.materials = materials;
        this.active = active;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_types_seq")
    @SequenceGenerator(name = "m_types_seq", sequenceName = "m_types_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "materialType", cascade = CascadeType.ALL)
    @OrderBy("id asc")
    @JsonIgnore
    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}