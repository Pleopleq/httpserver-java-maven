package com.example.httpserver;

import com.example.httpserver.config.Configuration;
import com.example.httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("SERVER STARTING...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfig();
        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());
    }
}
