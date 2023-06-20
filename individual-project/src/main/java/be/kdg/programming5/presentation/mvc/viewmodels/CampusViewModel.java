package be.kdg.programming5.presentation.mvc.viewmodels;

import be.kdg.programming5.domain.University;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class CampusViewModel {
    private long id;

    private University university;

    private String name;

    private String address;

    private int postalCode;

    private String city;

    private @DateTimeFormat(pattern = "hh:mm") LocalTime openingTime;

    private @DateTimeFormat(pattern = "hh:mm") LocalTime closingTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}
