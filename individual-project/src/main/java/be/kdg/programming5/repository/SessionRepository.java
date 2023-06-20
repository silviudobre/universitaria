package be.kdg.programming5.repository;

import be.kdg.programming5.domain.Session;

import java.util.List;

public interface SessionRepository {
    List<Session> readSessions();
    void createSession(Session session);
}
