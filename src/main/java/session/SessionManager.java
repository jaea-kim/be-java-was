package session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(String sessionId, Session session) {
        sessions.put(sessionId, session);
    }

    public static Optional<Session> findSessionByUserId(String sessionId) {
        return sessions.entrySet().stream()
                .filter(e -> sessionId.equals(e.getKey()))
                .map(Map.Entry::getValue)
                .findAny();
    }
}
