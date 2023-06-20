package be.kdg.programming5.presentation.mvc.viewmodels;

import be.kdg.programming5.domain.enums.UniversityType;

import java.time.LocalDate;

public class UniversityViewModel {
    private String name;
    private LocalDate foundingDate;
    private UniversityType universityType;

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

    public UniversityType getUniversityType() {
        return universityType;
    }

    public void setUniversityType(UniversityType universityType) {
        this.universityType = universityType;
    }
}
