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
 * entity Country
 * lookup for showing country details
 * @author NIBUN
 */
@ApiModel(description = "entity Country lookup for showing country details @author NIBUN")
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_name")
    private String sortName;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_code")
    private Integer phoneCode;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<State> states = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public Country sortName(String sortName) {
        this.sortName = sortName;
        return this;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public Country phoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public Set<State> getStates() {
        return states;
    }

    public Country states(Set<State> states) {
        this.states = states;
        return this;
    }

    public Country addStates(State state) {
        this.states.add(state);
        state.setCountry(this);
        return this;
    }

    public Country removeStates(State state) {
        this.states.remove(state);
        state.setCountry(null);
        return this;
    }

    public void setStates(Set<State> states) {
        this.states = states;
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
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", sortName='" + getSortName() + "'" +
            ", name='" + getName() + "'" +
            ", phoneCode=" + getPhoneCode() +
            "}";
    }
}
