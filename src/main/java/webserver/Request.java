package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private static final Request request = new Request();

    private static RequestLine requestLine;

    private Request() {}

    public static Request getRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = RequestLine.getRequestLine(br.readLine());

        return request;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public boolean isParam() {
        return requestLine.isParam();
    }

    public String getQueryString() {
        return requestLine.getQueryString();
    }
}
