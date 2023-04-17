package webserver;

import user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;

import java.io.IOException;
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

    public Response process(Request request) throws IOException {
        //데이터 조회
        if (request.getMethod().equals("GET")) {
            // 동적 데이터 조회
            if (request.isParam()) {
                //Todo: 나중에 동적 데이터 조회 필요하면 만들자, 현재는 없는 요청이라서 404 에러페이지 보내기
                return new Response(StatusCode.NOT_FOUND, "/error.html", request.getAccept());
            } else { //정적 데이터 조회
                logger.debug("request static data : {}", request.getPath());
                return new Response(StatusCode.OK, request.getPath(), request.getAccept());
            }
        } else {
            //데이터 전송 상황
            logger.debug("request data : {}", request.getPath());
            String url = getMapping(request);
            logger.debug("new mapping url : {}", url);
            return new Response(StatusCode.FOUND, url, request.getAccept());
        }
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
