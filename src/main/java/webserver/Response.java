package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Response {
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body = {};

    public Response(StatusCode statusCode, String requestPath, String accept) throws IOException {
        initResponse(statusCode, requestPath, accept);
    }

    private void initResponse(StatusCode statusCode, String requestPath, String accept) throws IOException {
        statusLine = statusCode.getStatusLine();

        if (requestPath.startsWith("redirect:")) {
            String path = requestPath.replace("redirect:", "");
            setHeader("Location", path);
        } else {
            ContentType contentType = ContentType.of(requestPath, accept);
            setHeader("Content-Type", contentType.getMimeTypeString());
            body = setMessageBody(contentType.getDirectoryPath(), requestPath);
            setHeader("Content-Length", String.valueOf(body.length));

        }
    }

    public void setHeader(String headerName, String value) {
        headers.put(headerName, value);
    }

    private byte[] setMessageBody(String directoryPath, String requestPath) throws IOException {
        return Files.readAllBytes(new File(directoryPath + requestPath).toPath());
    }

    public byte[] getMessageBody() {
        return body;
    }

    public String getHeaders() {
        return headers.keySet().stream()
                .map(key -> key + ": " + headers.get(key))
                .collect(Collectors.joining("\r\n"));
    }

    public String getMessageHeader() {
        return statusLine + "\r\n" + getHeaders() + "\r\n";
    }
}
