package com.diviso.infrastructure.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RackOrChair entity.
 */
public class RackOrChairDTO implements Serializable {

    private Long id;

    private String name;

    private Long shelfOrTableId;

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

    public Long getShelfOrTableId() {
        return shelfOrTableId;
    }

    public void setShelfOrTableId(Long shelfOrTableId) {
        this.shelfOrTableId = shelfOrTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RackOrChairDTO rackOrChairDTO = (RackOrChairDTO) o;
        if(rackOrChairDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rackOrChairDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RackOrChairDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
