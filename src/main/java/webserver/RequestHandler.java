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
            Response response;
            DataOutputStream dos = new DataOutputStream(out);
            RequestController requestController = RequestController.getRequestController();

            //정적 데이터 조회
            if (requestController.isMappingView(request)) {
                logger.debug("request static data : {}", request.getPath());
                response = new Response(200, request.getPath(), request.getAccept());
            } else {
                //동적 데이터 조회 및 데이터 전송 상황
                logger.debug("request data : {}", request.getPath());
                String url = requestController.getMapping(request);
                logger.debug("new mapping url : {}", url);
                response = new Response(302, url, request.getAccept());
            }

            sendResponse(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, Response response) throws IOException {
        dos.writeBytes(response.getMessageHeader());
        dos.writeBytes("\r\n");
        dos.write(response.getMessageBody(), 0, response.getMessageBody().length);
        dos.flush();
    }
}
