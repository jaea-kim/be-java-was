package session;

import java.util.Optional;
import java.util.UUID;

public class Session {
    private String sessionId;


    public Session getSession(String sessionId) {
        //sessionId 를 가진 세션 객체가 있는지 확인
        Optional<Session> session = SessionManager.findSessionByUserId(sessionId);
        //존재하면 해댱 세션 객체 반환
        if (session.isPresent()) {
            return session.get();
        }
        this.sessionId = sessionId;
        //없으면 해당 세션 아이디를 가진 세션 객체 생성하여 반환
        return this;
    }

    public void createSessionID() {
        UUID uuid = UUID.randomUUID();
        this.sessionId = uuid.toString();
    }

//    public void invalidate() {
//
//    }
}
