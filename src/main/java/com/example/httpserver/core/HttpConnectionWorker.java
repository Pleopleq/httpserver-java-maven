package com.example.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorker extends Thread {

    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorker.class);

    public HttpConnectionWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String HTMLPage = "<html><head><title>SIMPLE JAVA HTTP SERVER</title></head><body><h1>Page served using my simple Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r";
            String headers = "Content-Length: " + HTMLPage.getBytes().length;
            String httpResponse = "HTTP/1.1 200 OK " + CRLF + headers + CRLF + CRLF + HTMLPage + CRLF + CRLF;

            outputStream.write(httpResponse.getBytes());

            LOGGER.info("Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if(inputStream != null ) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
