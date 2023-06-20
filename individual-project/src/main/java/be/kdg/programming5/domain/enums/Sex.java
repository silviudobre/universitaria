package be.kdg.programming5.domain.enums;

public enum Sex {
    MALE("Male"),
    FEMALE("Female"),
    INTERSEX("Intersex"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String sex;
    Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }
}