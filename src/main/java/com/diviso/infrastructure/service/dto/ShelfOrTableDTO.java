package com.diviso.infrastructure.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShelfOrTable entity.
 */
public class ShelfOrTableDTO implements Serializable {

    private Long id;

    private String name;

    private Long sectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShelfOrTableDTO shelfOrTableDTO = (ShelfOrTableDTO) o;
        if(shelfOrTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shelfOrTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShelfOrTableDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
