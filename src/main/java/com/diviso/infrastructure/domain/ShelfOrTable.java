package com.diviso.infrastructure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * entity ShelfOrTable
 * To store ShelfOrTable details
 * @author NIBUN
 */
@ApiModel(description = "entity ShelfOrTable To store ShelfOrTable details @author NIBUN")
@Entity
@Table(name = "shelf_or_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShelfOrTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Section section;

    @OneToMany(mappedBy = "shelfOrTable")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RackOrChair> racksOrChairs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ShelfOrTable name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public ShelfOrTable section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<RackOrChair> getRacksOrChairs() {
        return racksOrChairs;
    }

    public ShelfOrTable racksOrChairs(Set<RackOrChair> rackOrChairs) {
        this.racksOrChairs = rackOrChairs;
        return this;
    }

    public ShelfOrTable addRacksOrChairs(RackOrChair rackOrChair) {
        this.racksOrChairs.add(rackOrChair);
        rackOrChair.setShelfOrTable(this);
        return this;
    }

    public ShelfOrTable removeRacksOrChairs(RackOrChair rackOrChair) {
        this.racksOrChairs.remove(rackOrChair);
        rackOrChair.setShelfOrTable(null);
        return this;
    }

    public void setRacksOrChairs(Set<RackOrChair> rackOrChairs) {
        this.racksOrChairs = rackOrChairs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShelfOrTable shelfOrTable = (ShelfOrTable) o;
        if (shelfOrTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shelfOrTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShelfOrTable{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
