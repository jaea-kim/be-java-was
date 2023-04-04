package webserver;

import util.HttpParser;

public class RequestLine {
    private String method;
    private String requestTarget;

    public RequestLine(String requestLine) {
        method = HttpParser.getMethodInRequestLine(requestLine);
        requestTarget = HttpParser.getRequestTargetInRequestLine(requestLine);
    }

    public String getPath() {
        if (isParam()) {
            return HttpParser.getAbsolutePath(requestTarget);
        }
        return requestTarget;
    }

    public String getMethod() {
        return method;
    }

    public boolean isParam() {
        return requestTarget.contains("?");
    }
}
