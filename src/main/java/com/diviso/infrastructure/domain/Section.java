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
 * entity Section
 * To store Section details
 * @author NIBUN
 */
@ApiModel(description = "entity Section To store Section details @author NIBUN")
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Floor floor;

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShelfOrTable> shelvesOrTables = new HashSet<>();

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

    public Section name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Floor getFloor() {
        return floor;
    }

    public Section floor(Floor floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Set<ShelfOrTable> getShelvesOrTables() {
        return shelvesOrTables;
    }

    public Section shelvesOrTables(Set<ShelfOrTable> shelfOrTables) {
        this.shelvesOrTables = shelfOrTables;
        return this;
    }

    public Section addShelvesOrTables(ShelfOrTable shelfOrTable) {
        this.shelvesOrTables.add(shelfOrTable);
        shelfOrTable.setSection(this);
        return this;
    }

    public Section removeShelvesOrTables(ShelfOrTable shelfOrTable) {
        this.shelvesOrTables.remove(shelfOrTable);
        shelfOrTable.setSection(null);
        return this;
    }

    public void setShelvesOrTables(Set<ShelfOrTable> shelfOrTables) {
        this.shelvesOrTables = shelfOrTables;
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
        Section section = (Section) o;
        if (section.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
