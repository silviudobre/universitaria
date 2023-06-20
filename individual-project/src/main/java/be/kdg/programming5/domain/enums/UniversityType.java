package be.kdg.programming5.domain.enums;

public enum UniversityType {
    HOGESCHOOL("Hogeschool"),
    UNIVERSITEIT("Universiteit");
    private final String universityType;

    UniversityType(String universityType) {
        this.universityType = universityType;
    }

    @Override
    public String toString() {
        return universityType;
    }
}
