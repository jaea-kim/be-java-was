package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HttpParserTest {
    private static final String START_LINE_WITH_QUERY = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private static final String START_LINE_NO_QUERY = "GET /index.html";

    @Test
    @DisplayName("request line에 query string이 있으면 method를 반환해야한다.")
    void getMethodInRequestLineWithQuery() {
        String startLineWithQuery = HttpParser.getMethodInRequestLine(START_LINE_WITH_QUERY);
        assertThat(startLineWithQuery).isEqualTo("GET");
    }

    @Test
    @DisplayName("request line에 query string이 없어도 method를 반환해야한다.")
    void getMethodInRequestLineWithNOQuery() {
        String startLineWithNoQuery = HttpParser.getMethodInRequestLine(START_LINE_NO_QUERY);
        assertThat(startLineWithNoQuery).isEqualTo("GET");
    }

    @Test
    @DisplayName("request line에 query string이 있으면 query string을 포함해서 request target을 반환해야한다.")
    void getRequestTargetInRequestLineWithQuery() {
        String startLineWithQuery = HttpParser.getRequestTargetInRequestLine(START_LINE_WITH_QUERY);
        assertThat(startLineWithQuery).isEqualTo("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    @DisplayName("request line에 query string이 없으면 path만 반환해야한다.")
    void getRequestTargetInRequestLineWithNoQuery() {
        String startLineWithQuery = HttpParser.getRequestTargetInRequestLine(START_LINE_NO_QUERY);
        assertThat(startLineWithQuery).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("request target에 query string이 있으면 query string을 제외한 절대경로만 반환해야한다.")
    void getAbsolutePathWithQueryString() {
        String requestTarget = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String path = HttpParser.getAbsolutePath(requestTarget);
        assertThat(path).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("request target에 query string이 없으면 request target 그대로 반환해야한다.")
    void getAbsolutePathWithNoQueryString() {
        String requestTarget = "/index.html";
        String path = HttpParser.getAbsolutePath(requestTarget);
        assertThat(path).isEqualTo(requestTarget);
    }

    @Test
    @DisplayName("request target에 query string이 있으면 절대경로는 제외한 query string만 반환해야한다.")
    void getQueryStringWithQueryString() {
        String requestTarget = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String queryString = HttpParser.getQueryString(requestTarget);
        assertThat(queryString).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
