package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.domain.Session;

import java.util.List;

public interface SessionService {
    List<Session> getSessions();
    void addSession(Session session);
}
