package be.kdg.programming5.presentation.api.dtos;

import be.kdg.programming5.domain.enums.UniversityType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class NewUniversityDto {
    @NotNull
    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z])")
    private String name;
    @NotNull
    private @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate foundingDate;
//    @NotNull
    private UniversityType universityType;

    public NewUniversityDto() {
    }

    public NewUniversityDto(String name, LocalDate foundingDate, UniversityType universityType) {
        this.name = name;
        this.foundingDate = foundingDate;
        this.universityType = universityType;
    }

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

    @Override
    public String toString() {
        return "UniversityDto{" +
                "name='" + name + '\'' +
                ", foundingDate=" + foundingDate +
                ", universityType='" + universityType + '\'' +
                '}';
    }
}
