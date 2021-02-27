package com.sber.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import okhttp3.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

        OkHttpClient httpClient = new OkHttpClient();
        okhttp3.RequestBody formBody = okhttp3.RequestBody.create(MediaType.parse("application/json"), json);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*");

        if (json != null) {
            builder.post(formBody);
        }

        Request request = builder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String result = Objects.requireNonNull(response.body()).string();
            logger.info(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"status\":\"error\"}";
    }

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
