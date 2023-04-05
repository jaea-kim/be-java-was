package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;

import javax.sql.DataSource;

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
            //입력으로 들어온 내용 전부 request로 보냄
            Request request = new Request(in);
            //받은 request를 가지고 requestMapping을 해주는 requestController 생성
            RequestController requestController = new RequestController(request);
            DataOutputStream dos = new DataOutputStream(out);

            //requestcontroller에서 다 처리 후 결론적으로 보여줘야할 뷰 경로를 알려주는게 맞지 않을까,, + 상태코드까지?
            if (requestController.isMappingView()) {
                logger.debug("in static view");
                Response response = new Response("200", request.getPath());
                sendResponse(dos, response);

            } else {
                requestController.mapping();
            }


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
