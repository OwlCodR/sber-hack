package com.sber.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Tag(name="Основные запросы", description="Здесь представлены основные методы для взаимодействия")
@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final String IP = "http://82.146.61.94:";

    private String getJsonStringFromServer(String uri) {
        StringBuilder result = new StringBuilder();
        try {
            logger.info(uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*");
            logger.info(conn.getResponseCode() + "");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpStatus.BAD_REQUEST + "";
    }

    private String sendJsonToServer(String url, String json, String requestMethod) {
        logger.info(url);
        logger.info(requestMethod);
        logger.info(json);

        HttpURLConnection httpClient = null;
        StringBuilder result = new StringBuilder();
        try {
            httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod(requestMethod);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "*/*");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            //
            httpClient.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                wr.writeBytes(json);
                wr.flush();
            }


            BufferedReader rd = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();


            logger.info(httpClient.getResponseCode() + "");
            logger.info(httpClient.getResponseCode() + "");
            logger.info(httpClient.getResponseMessage() + "");
            logger.info(result + "");
            return result + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    @Operation(
            summary = "Получить объект пользователя по ID",
            description = "Возвращает JSON строку с полями объекта класса User с уникальным ID (Example: user.15)")
    @RequestMapping(value = "/api/users/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getUser(@PathVariable("id") int id) {
        String port = "8081";
        return getJsonStringFromServer(IP + port + "/users/user." + id);
    }

    @Operation(
            summary = "Создать объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PostMapping(value = "/api/users", consumes = "application/json")
    public void createUser(@RequestBody String json) {
        String port = "8081";
        sendJsonToServer(IP + port + "/users", json, "POST");
    }

    @PostMapping(value = "/api/questions", produces = "application/json")
    public String setQuestion(@RequestBody String json) {
        String port = "8082";
        return sendJsonToServer(IP + port + "/questions", StringEscapeUtils.unescapeHtml(json), "POST");
    }

    @Operation(
            summary = "Обновить объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PutMapping (value = "/api/users", consumes = "application/json")
    public void updateUser(@RequestBody String json) {
        String port = "8081";
        sendJsonToServer(IP + port + "/users", json, "PUT");
    }

    @Operation(
            summary = "Получить объект вопроса",
            description = "Возвращает JSON с полями объекта класса Question")
    @RequestMapping(value = "/api/questions/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getQuestion(@PathVariable("id") int id) {
        String port = "8082";
        return getJsonStringFromServer(IP + port + "/questions/" + id);
    }



    @PutMapping(value = "/api/questions", produces = "application/json")
    public void updateQuestion(@RequestBody String json) {
        String port = "8082";
        sendJsonToServer(IP + port + "/questions", json, "PUT");
    }
    //http://localhost:8080/api/questions
}
