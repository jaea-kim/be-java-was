package util;

import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    public static String getMethodInRequestLine(String requestLine) {
        return requestLine.split(" ")[0];
    }

    public static String getRequestTargetInRequestLine(String requestLine) {
        return requestLine.split(" ")[1];
    }

    public static String getAbsolutePath(String requestTarget) {
        return requestTarget.split("[?]")[0];
    }

    public static String getQueryString(String requestTarget) {
        return requestTarget.split("[?]")[1];
    }

    public static Map<String, String> getParam(String queryString) {
        Map<String, String> params = new HashMap<>();
        String[] querys = queryString.split("&");

        for (String query : querys) {
            String[] q = query.split("=");
            params.put(q[0], q[1]);
        }

        return params;
    }
}
