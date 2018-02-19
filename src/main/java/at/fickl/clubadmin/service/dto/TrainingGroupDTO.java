package at.fickl.clubadmin.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TrainingGroup entity.
 */
public class TrainingGroupDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingGroupDTO trainingGroupDTO = (TrainingGroupDTO) o;
        if(trainingGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
