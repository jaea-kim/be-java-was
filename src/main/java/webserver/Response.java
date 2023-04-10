package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Response {
    private final String templatesResourcePath = "src/main/resources/templates";
    private final String staticResourcePath = "src/main/resources/static";
    private StatusCode statusCode;
    private byte[] body;

    public Response(int code, String requestPath) throws IOException {
        this.statusCode = StatusCode.getStatusCode(code);
        this.body = Files.readAllBytes(new File(templatesResourcePath + requestPath).toPath());
    }

    public String getResponseHeader() {
        return statusCode.getStatusLine() + getHeader();
    }

    public byte[] getResponseBody() {
        return body;
    }

    private String getOKStatusLine() {
        return statusCode.getStatusLine();
    }

    private String getHeader() {
        return "Content-Type: text/html;charset=utf-8\r\nContent-Length: " + body.length + "\r\n";
    }
}
