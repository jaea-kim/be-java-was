package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            //입력으로 들어온 내용을 매개변수로 받아 request 객체 생성
            Request request = Request.getRequest(in);
            DataOutputStream dos = new DataOutputStream(out);
            Response response;
            RequestController requestController = RequestController.getRequestController();

            //request가 바로 view와 연결될 수 있을 때
            if (requestController.isMappingView(request)) {
                logger.debug("request .html");
                response = new Response("200", request.getPath());
            } else {
                //리소스 내용 처리하고 최종 url 받아 넘겨줌
                String url = requestController.getMapping(request);
                logger.debug("new mapping url : {}", url);
                response = new Response("200", url);
            }

            sendResponse(dos, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, Response response) throws IOException {
        byte[] body = response.getResponseBody();

        dos.writeBytes(response.getResponseHeader());
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
