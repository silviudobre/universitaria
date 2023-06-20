package be.kdg.programming5.presentation.mvc.viewmodels;

import be.kdg.programming5.domain.enums.Sex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewTeacherViewModel {
    @NotNull
    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z])", message = "First name must start with a letter and can contain only letters and spaces")
    private String firstName;
    @NotNull
    @Pattern(regexp = "([A-Za-z]|[A-Za-z][A-Za-z ]*[A-Za-z])", message = "Last name must start with a letter and can contain only letters and spaces")
    private String lastName;
    @NotNull
    private Sex sex;

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return String.format("[Teacher: %s %s | %s]",
                firstName, lastName, sex);
    }
}
