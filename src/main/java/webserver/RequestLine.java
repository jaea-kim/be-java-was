package webserver;

import util.HttpParser;

public class RequestLine {
    private static String method;
    private static String requestTarget;

    public RequestLine(String startLine) {
        method = HttpParser.getMethodInRequestLine(startLine);
        requestTarget = HttpParser.getRequestTargetInRequestLine(startLine);
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

    public String getQueryString() {
        return HttpParser.getQueryString(requestTarget);
    }
}
