package be.kdg.programming5.domain;

import be.kdg.programming5.domain.enums.UniversityType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class University {
    @Id
    @Column
    private String name;

    @Column
    private LocalDate foundingDate;
    @Enumerated(EnumType.STRING)
    @Column
    private UniversityType universityType;

    @OneToMany
    private List<Campus> campuses;

    public University(String name, LocalDate foundingDate, UniversityType universityType) {
        campuses = new ArrayList<>();

        this.name = name;
        this.foundingDate = foundingDate;
        this.universityType = universityType;
    }

    public University() {

    }

    public List<Campus> getCampuses() {
        return campuses;
    }

    public String getName() {
        return name;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public UniversityType getUniversityType() {
        return universityType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public void setUniversityType(UniversityType universityType) {
        this.universityType = universityType;
    }

    public void addCampus(Campus campus) {
        this.campuses.add(campus);
    }



    @Override
    public String toString() {
        return String.format("[University: %s %s | founded in %TF]",
                name, universityType, foundingDate);
    }
}
