package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);
    private RequestLine requestLine;
    private Map<String, String> header;

    public Request(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = new RequestLine(br.readLine());
        header = new HashMap<>();

        String headerString;
        while (!((headerString = br.readLine()).equals(""))) {
            initHeader(headerString);
        }
    }

    private void initHeader(String headerString) {
        String[] h = headerString.split(":", 2);
        header.put(h[0], h[1]);
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

    public String getAccept() {
        return header.get("Accept");
    }
}
