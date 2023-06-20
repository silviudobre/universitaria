package be.kdg.programming5.presentation.api.dtos;

import java.time.LocalDate;

public class UniversityDto {
    private String name;
    private LocalDate foundingDate;
    private String universityType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    @Override
    public String toString() {
        return "UniversityDto{" +
                "name='" + name + '\'' +
                ", foundingDate=" + foundingDate +
                ", universityType='" + universityType + '\'' +
                '}';
    }
}
