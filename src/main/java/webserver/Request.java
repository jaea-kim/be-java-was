package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.Session;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private RequestLine requestLine;
    private Map<String, String> header;
    private String body;
    private Session session;

    public Request(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = new RequestLine(br.readLine());
        header = new HashMap<>();
        session = new Session();

        String headerString;
        while (!((headerString = br.readLine()).equals(""))) {
            initHeader(headerString);
        }

        if (header.containsKey("Content-Length")) {
            initBody(br);
        }
        //쿠키 헤더가 존재하면 쿠키에 저장된 세션 아이디를 가지고 세션 객체 찾기
        if (header.containsKey("Cookie")) {
            initSession(header.get("Cookie"));
        }
    }

    private void initSession(String cookieHeader) {
        String[] attributes = cookieHeader.split(" ");
        for (String attribute : attributes) {
            if (attribute.startsWith("SID")) {
                String sid = attribute.replace("SID=", "").trim();
                session = session.getSession(sid);
            }
        }
    }

    private void initBody(BufferedReader br) throws IOException {
        int contentLength = Integer.parseInt(header.get("Content-Length").trim());
        char[] bodyB = new char[contentLength];
        int length = br.read(bodyB);
        body = String.valueOf(bodyB);
    }

    private void initHeader(String headerString) {
        String[] h = headerString.split(":", 2);
        header.put(h[0], h[1]);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public boolean isParam() {
        return requestLine.isParam();
    }

    public String getQueryString() {
        return requestLine.getQueryString();
    }

    public String getAccept() {
        return header.get("Accept").split(",")[0].trim();
    }

    public String getBody() {
        return body;
    }

    public Session getSession() {
        return session;
    }
}
