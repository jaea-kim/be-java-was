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
            Request request = new Request(in);
            DataOutputStream dos = new DataOutputStream(out);
            Response response;
            RequestController requestController = RequestController.getRequestController();

            //정적 데이터 조회
            if (requestController.isMappingView(request)) {
                logger.debug("request static data");
                logger.debug("request accept : {}", request.getAccept());
                response = new Response(200, request.getPath());
            } else {
                //동적 데이터 조회 및 데이터 전송 상황
                String url = requestController.getMapping(request);
                logger.debug("new mapping url : {}", url);
                logger.debug("request accept : {}", request.getAccept());
                response = new Response(200, url);
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
