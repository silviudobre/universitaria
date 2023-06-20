package be.kdg.programming5.domain.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER"),TEACHER("ROLE_TEACHER");
    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
