package webserver;

import user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;

import java.util.Map;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private RequestController(){
    }

    private static class RequestControllerHelper {
        private static final RequestController INSTANCE = new RequestController();
    }

    public static RequestController getRequestController() {
        return RequestControllerHelper.INSTANCE;
    }

    public boolean isRequestGet(Request request) {
        return request.getMethod().equals("GET");
    }

    public boolean isRequestStatic(Request request) {
        if (request.getMethod().equals("GET")) {
            return !request.isParam();
        }
        return false;
    }

    public String getMapping(Request request) {
        logger.debug("path : {}", request.getPath());
        if (request.getPath().startsWith("/user/create")) {
            UserController userController = UserController.getUserController();

            Map<String, String> params = HttpParser.getParam(request.getBody());
            String url = userController.join(params);

            return url;
        }
        return "/error.html";
    }
}
