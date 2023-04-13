package webserver;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", ".js", "/static"),
    CSS("text/css;charset=utf-8", ".css", "/static"),
    PNG("image/png", ".png", "/static"),
    ICO("image/avif", ".ico", "/static"),
    HTML("text/html;charset=utf-8", ".html", "/templates"),
    FONT("font/woff", ".woff", "/static");

    private String mimeType;
    private String extension;
    private String path;
    private final String RESOURCES_PATH = "src/main/resources";

    ContentType(String mimeType, String extension, String path) {
        this.mimeType = mimeType;
        this.extension = extension;
        this.path = path;
    }

    public static ContentType of(String requestPath, String accept) {
        //accept에 모든 mime 타입을 허용하면 파일의 확장자로 결정
        if (accept.contains("*")) {
            return Arrays.stream(values())
                    .filter(contentType -> requestPath.endsWith(contentType.extension))
                    .findAny().orElse(HTML);
        }
        //기본적으로 accept가 content-type이 됨
        return Arrays.stream(values())
                .filter(contentType -> contentType.mimeType.contains(accept))
                .findAny().orElse(HTML);
    }

    public String getDirectoryPath() {
        return RESOURCES_PATH + path;
    }

    public String getMimeTypeString() {
        return mimeType;
    }
}
