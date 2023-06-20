package be.kdg.programming5.presentation.api.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

public class UpdateCampusDto {
    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z])", message = "Campus must start with a letter and can contain only letters and spaces")
    private String name;

    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z]) [0-9]+", message = "Address must start with a letter, end with a number, and can contain only letters and spaces in rest")
    private String address;

    @Min(value = 1, message = "Enter a valid postal code")
    private Integer postalCode;

    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z])", message = "City must start with a letter and can contain only letters and spaces")
    private String city;

    private @DateTimeFormat(pattern = "hh:mm") LocalTime openingTime;

    private @DateTimeFormat(pattern = "hh:mm") LocalTime closingTime;

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

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
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

    @Override
    public String toString() {
        return "UpdateCampusDto{" +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                '}';
    }
}
