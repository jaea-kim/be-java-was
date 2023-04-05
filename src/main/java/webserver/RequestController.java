package webserver;

import User.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;

import java.util.Map;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private UserController userController = new UserController();
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

    public String getMapping() {
        logger.debug("path : {}", request.getPath());
        if (request.getPath().startsWith("/user/create")) {
            Map<String, String> params = HttpParser.getParam(request.getQueryString());
            String url = userController.join(params);

            return url;
        }
        return "/";
    }
}
