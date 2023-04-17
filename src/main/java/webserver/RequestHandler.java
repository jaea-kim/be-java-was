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

            RequestController requestController = RequestController.getRequestController();

            Response response = requestController.process(request);
            sendResponse(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(DataOutputStream dos, Response response) throws IOException {
        dos.writeBytes(response.getMessageHeader());
        dos.writeBytes("\r\n");
        if (response.getMessageBody() != null) {
            dos.write(response.getMessageBody(), 0, response.getMessageBody().length);
        }
        dos.flush();
    }
}
