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
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;


@Tag(name="Основные запросы", description="Здесь представлены основные методы для взаимодействия")
@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final String IP = "http://82.146.61.94:";

    private static final String PORT_USERS = "8081";
    private static final String PORT_QUESTIONS = "8082";

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";

    private String makeRequest(String url, String json, String requestMethod) {
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

            if (json != null) {
                httpClient.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                    wr.writeBytes(json);
                    wr.flush();
                }
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            logger.info(httpClient.getResponseCode() + "");
            logger.info(result.toString());
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"error\"}";
    }

    /*
    private String getJsonStringFromServer(String uri) {
        StringBuilder result = new StringBuilder();
        try {
            logger.info(uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "/");
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
            httpClient.setRequestProperty("Accept", "");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

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
    */

    @Operation(
            summary = "Получить объект пользователя по ID",
            description = "Возвращает JSON строку с полями объекта класса User с уникальным ID (Example: user.15)")
    @RequestMapping(value = "/api/users/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getUser(@PathVariable("id") int id) {
        return makeRequest(IP + PORT_USERS + "/users/user." + id, null, GET);
    }

    @Operation(
            summary = "Создать объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PostMapping(value = "/api/users", consumes = "application/json")
    public String createUser(@RequestBody String json) {
        return makeRequest(IP + PORT_USERS + "/users", json, POST);
    }

    @Operation(
            summary = "Обновить объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PutMapping(value = "/api/users", consumes = "application/json")
    public String updateUser(@RequestBody String json) {
        return makeRequest(IP + PORT_USERS + "/users", json, PUT);
    }

    @Operation(
            summary = "Создать объект вопроса",
            description = "Получает JSON с полями объекта класса Question")
    @PostMapping(value = "/api/questions", produces = "application/json")
    public String createQuestion(@RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/questions", json, POST);
    }

    @Operation(
            summary = "Получить объект вопроса",
            description = "Возвращает JSON с полями объекта класса Question")
    @RequestMapping(value = "/api/questions/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getQuestion(@PathVariable("id") int id) {
        return makeRequest(IP + PORT_QUESTIONS + "/questions/" + id, null, GET);
    }

    @Operation(
            summary = "Возвращает массив вопросов в формате JSON",
            description = "Получает JSON с полями фильтров")
    @GetMapping(value = "/api/questions/filter", produces = "application/json")
    public String getQuestions(@RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/questions/filters", json, GET);
    }

    @Operation(
            summary = "Обновить объект вопроса",
            description = "Получает JSON с полями объекта класса Question")
    @PutMapping(value = "/api/questions", produces = "application/json")
    public String updateQuestion(@RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/questions", json, PUT);
    }
}
