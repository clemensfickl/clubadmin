package at.fickl.clubadmin.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ContributionGroupEntry entity.
 */
public class ContributionGroupEntryDTO implements Serializable {

    private Long id;

    private Integer year;

    private BigDecimal amount;

    private Long groupId;

    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long contributionGroupId) {
        this.groupId = contributionGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String contributionGroupName) {
        this.groupName = contributionGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContributionGroupEntryDTO contributionGroupEntryDTO = (ContributionGroupEntryDTO) o;
        if(contributionGroupEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contributionGroupEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContributionGroupEntryDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", amount=" + getAmount() +
            "}";
    }
}
