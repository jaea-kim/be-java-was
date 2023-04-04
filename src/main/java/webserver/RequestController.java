package webserver;

public class RequestController {
    private Request request;

    RequestController(Request request) {
        this.request = request;
    }

    public boolean isMappingView() {
        if (request.getMethod().equals("GET")) {
            return !request.isParam();
        }
        return false;
    }

    public void mapping() {
        if (request.getMethod().equals("GET")) {

        }
    }
}
