package com.service.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TestUtils {

    public static <T> String mapToJsonString(T requestObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestObject);
    }

    public static <T> T mapFromJsonString(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        T t = objectMapper.readValue(json, clazz);
        return t;
    }

    public static String readJsonStringFromFilePath(String filePath) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        String request = FileUtils.readFileToString(file, "UTF-8");

        return request;
    }

}
