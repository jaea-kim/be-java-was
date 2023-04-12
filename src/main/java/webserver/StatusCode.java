package webserver;

import java.util.Arrays;
import java.util.Map;

public enum StatusCode {
    OK(200, "200 OK"),
    FOUND(302, "302 FOUND");

    private int code;
    private String statusCode;

    StatusCode(int code, String statusCode) {
        this.code = code;
        this.statusCode = statusCode;
    }

    public static StatusCode getStatusCode(int code) {
        return Arrays.stream(values())
                .filter(value -> value.code == code)
                .findAny()
                .orElse(null);
    }

    public String getStatusLine() {
        return "HTTP/1.1 " + statusCode;
    }
}
