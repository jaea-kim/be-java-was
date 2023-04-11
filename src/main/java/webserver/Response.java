package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Response {
    private final String templatesResourcePath = "src/main/resources/templates";
    private final String staticResourcePath = "src/main/resources/static";
    private String statusLine;
    private String headers;
    private byte[] body;

    public Response(int code, String requestPath, String accept) throws IOException {
        initResponse(code, requestPath, accept);
    }

    private void initResponse(int code, String requestPath, String accept) throws IOException {
        if (code == 200) {
            statusLine = StatusCode.OK.getStatusLine();
            body = setMessageBody(accept, requestPath);
            headers = set200Header(accept, body.length);
        } else if (code == 302) {
            statusLine = StatusCode.FOUND.getStatusLine();
            headers = set302Header(requestPath);
        }
    }

    private String set200Header(String accept, int bodyLength) {
        return "Content-Type:" + accept + ";charset=utf-8\r\nContent-Length: " + bodyLength + "\r\n";
    }

    private String set302Header(String url) {
        return "Location: " + url;
    }

    private byte[] setMessageBody(String accept, String requestPath) throws IOException {
        if (accept.contains("html")) {
            return Files.readAllBytes(new File(templatesResourcePath + requestPath).toPath());
        } else {
            return Files.readAllBytes(new File(staticResourcePath + requestPath).toPath());
        }
    }

    public byte[] getMessageBody() {
        return body;
    }

    public String getMessageHeader() {
        return statusLine + "\n" + headers;
    }
}
