package be.kdg.programming5.service;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.repository.SessionRepository;
import be.kdg.programming5.service.interfaces.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getSessions() {
        return sessionRepository.readSessions();
    }

    @Override
    public void addSession(Session session) {
        sessionRepository.createSession(session);
    }
}
