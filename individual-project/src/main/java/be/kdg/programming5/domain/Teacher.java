package be.kdg.programming5.domain;

import be.kdg.programming5.domain.enums.Sex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Course> courses;

    @Column
    private String firstName;

    @Column
    private String lastName;
//    private String discipline;
    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn
    private User user;


    public Teacher(String firstName, String lastName, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;

        this.courses = new ArrayList<>();
    }

    public Teacher(long id, String firstName, String lastName, Sex sex) {
        this.id = id;
//        jobLocations = new ArrayList<>();

        this.firstName = firstName;
        this.lastName = lastName;
//        this.discipline = discipline;
        this.sex = sex;
    }

    public Teacher() {

    }

    public long getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setId(long teacherId) {
        this.id = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("[Teacher: %s %s | %s]",
                firstName, lastName, sex);
    }
}
