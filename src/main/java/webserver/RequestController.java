package webserver;

import session.Session;
import user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;

import java.io.IOException;
import java.util.Map;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private RequestController() {
    }

    private static class RequestControllerHelper {
        private static final RequestController INSTANCE = new RequestController();
    }

    public static RequestController getRequestController() {
        return RequestControllerHelper.INSTANCE;
    }

    public Response process(Request request) throws IOException {
        //Todo: http 메소드에 해당하는 로직을 여기서 다 처리하는 것이 좋을까? 더 좋은 방법이 없을까 고민하기
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
        } else if (request.getMethod().equals("POST")) {
            //POST 요청 처리
            logger.debug("request data : {}", request.getPath());
            return postMapping(request);
        } else {
            //Todo: delete와 put 요청이 필요할 때 만들자, 현재는 없는 요청이라서 404 에러페이지 보내기
            return new Response(StatusCode.NOT_FOUND, "/error.html", request.getAccept());
        }
    }

    private Response postMapping(Request request) throws IOException {
        logger.debug("path : {}", request.getPath());
        String resource = request.getPath();
        if (resource.startsWith("/user")) {
            UserController userController = UserController.getUserController();
            resource = resource.replace("/user", "");
            Map<String, String> params = HttpParser.getParam(request.getBody());
            if (resource.startsWith("/create")) {
                String url = userController.join(params);

                return new Response(StatusCode.FOUND, url, request.getAccept());
            } else if (resource.startsWith("/login")) {
                Session session = request.getSession();
                String url = userController.login(params, session);

                return new Response(StatusCode.FOUND, url, request.getAccept());
            }
        }
        return new Response(StatusCode.NOT_FOUND, "/error.html", request.getAccept());
    }
}
