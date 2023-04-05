package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Response {
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String templatesResourcePath = "src/main/resources/templates";
    private String statusCode;
    private byte[] body;

    public Response(String statusCode, String requestPath) throws IOException {
        this.statusCode = statusCode;
        this.body = Files.readAllBytes(new File(templatesResourcePath + requestPath).toPath());
    }

    public String getResponseHeader() {
        return getStatusLine() + getHeader();
    }

    public byte[] getResponseBody() {
        return body;
    }

    private String getStatusLine() {
        return HTTP_VERSION +
                " " +
                statusCode +
                " " +
                getReasonPhrase();
    }

    private String getReasonPhrase() {
        if (statusCode.equals("200")) {
            return "OK";
        }
        return "ERROR";
    }

    private String getHeader() {
        return "Content-Type: text/html;charset=utf-8\r\nContent-Length: " + body.length + "\r\n";
    }
}
