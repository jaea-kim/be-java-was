package util;

public class HttpParser {
    public static String getPathInRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");

        return tokens[1];
    }
}
