package be.kdg.programming5.exceptions;

public class CampusNotFoundException extends RuntimeException{
    public CampusNotFoundException(String message) {
        super(message);
    }
}
