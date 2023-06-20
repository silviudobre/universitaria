package be.kdg.programming5.repository;

import be.kdg.programming5.domain.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionRepositoryImpl implements SessionRepository {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public List<Session> readSessions() {
        return sessions;
    }

    @Override
    public void createSession(Session session) {
        sessions.add(session);
    }
}
