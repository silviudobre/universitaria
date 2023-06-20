package be.kdg.programming5.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Course> courses;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private University university;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private int postalCode;

    @Column
    private String city;

    @Column
    private @DateTimeFormat(pattern = "hh:mm") LocalTime openingTime;

    @Column
    private @DateTimeFormat(pattern = "hh:mm") LocalTime closingTime;

    public Campus(String name, String address, int postalCode, String city, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.openingTime = openingTime;
        this.closingTime = closingTime;

        this.courses = new ArrayList<>();
    }

    public Campus() {

    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public University getUniversity() {
        return university;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setId(long campusId) {
        this.id = campusId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }


    @Override
    public String toString() {
        return String.format("[Campus: %s | address %d %s, %s | working hours %s to %s]",
                name, postalCode, address, city, openingTime, closingTime);
    }
}
