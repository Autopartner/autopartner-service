package autopartner.domain.entity;

import autopartner.domain.base.DomainBase;

import javax.persistence.*;

@Entity
@Table(name = "materials")
public class Material extends DomainBase {

    private Long id;
    private MaterialType materialType;
    private String name;
    private Boolean active;

    public Material() {
        super();
    }

    public Material(Long id, MaterialType materialType, String name, Boolean active) {
        this.id = id;
        this.materialType = materialType;
        this.name = name;
        this.active = active;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq")
    @SequenceGenerator(name = "materials_seq", sequenceName = "materials_seq", allocationSize = 1)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "m_type_id")
    @ManyToOne
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
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