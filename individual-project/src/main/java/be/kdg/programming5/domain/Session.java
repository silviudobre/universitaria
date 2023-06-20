package be.kdg.programming5.domain;

import java.sql.Timestamp;

public class Session {
    private String page;
    private final Timestamp timestamp;

    public Session(String page, Timestamp timestamp) {
        this.page = page;
        this.timestamp = timestamp;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
