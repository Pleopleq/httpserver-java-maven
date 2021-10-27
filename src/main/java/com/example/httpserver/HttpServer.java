package com.example.httpserver;

import com.example.httpserver.config.Configuration;
import com.example.httpserver.config.ConfigurationManager;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("SERVER STARTING...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfig();
        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String HTMLPage = "<html><head><title>SIMPLE JAVA HTTP SERVER</title></head><body><h1>Page served using my simple Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r";
            String headers = "Content-Length: " + HTMLPage.getBytes().length;
            String httpResponse = "HTTP/1.1 200 OK " + CRLF + headers + CRLF + CRLF + HTMLPage + CRLF + CRLF;

            outputStream.write(httpResponse.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
