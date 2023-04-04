package util;

public class HttpParser {
    public static String getMethodInRequestLine(String requestLine) {
        return requestLine.split(" ")[0];
    }

    public static String getRequestTargetInRequestLine(String requestLine) {
        return requestLine.split(" ")[1];
    }

    public static String getAbsolutePath(String requestTarget) {
        return requestTarget.split("//?")[0];
    }

    public static String getQuery(String requestTarget) {
        return requestTarget.split("//?")[1];
    }
}
