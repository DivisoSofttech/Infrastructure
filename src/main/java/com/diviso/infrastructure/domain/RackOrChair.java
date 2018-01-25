package com.diviso.infrastructure.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * entity RackOrChair
 * To store RackOrChair details
 * @author NIBUN
 */
@ApiModel(description = "entity RackOrChair To store RackOrChair details @author NIBUN")
@Entity
@Table(name = "rack_or_chair")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RackOrChair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private ShelfOrTable shelfOrTable;

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

    public RackOrChair name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShelfOrTable getShelfOrTable() {
        return shelfOrTable;
    }

    public RackOrChair shelfOrTable(ShelfOrTable shelfOrTable) {
        this.shelfOrTable = shelfOrTable;
        return this;
    }

    public void setShelfOrTable(ShelfOrTable shelfOrTable) {
        this.shelfOrTable = shelfOrTable;
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
        RackOrChair rackOrChair = (RackOrChair) o;
        if (rackOrChair.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rackOrChair.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RackOrChair{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
