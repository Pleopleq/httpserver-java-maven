package com.example.httpserver.config;

import com.example.httpserver.util.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if(myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }

        StringBuffer stringBuffer = new StringBuffer();
        int i;

            try {
                while ((i = fileReader.read()) != -1) {
                    stringBuffer.append((char)i);
                }
            } catch (IOException e) {
                throw new HttpConfigurationException("", e);
            }

        JsonNode config = null;
        try {
            config = JSON.parse(stringBuffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }

        try {
            myCurrentConfiguration = JSON.fromJson(config, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File, internal", e);
        }
    }

    public Configuration getCurrentConfig() throws HttpConfigurationException {
        if(myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}

