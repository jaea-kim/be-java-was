package webserver;

import util.HttpParser;

public class RequestLine {
    private static final RequestLine requestLine = new RequestLine();
    private static String method;
    private static String requestTarget;

    private RequestLine(){
    }

    public static RequestLine getRequestLine(String startLine) {
        method = HttpParser.getMethodInRequestLine(startLine);
        requestTarget = HttpParser.getRequestTargetInRequestLine(startLine);

        return requestLine;
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
