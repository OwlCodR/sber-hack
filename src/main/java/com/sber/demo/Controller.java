package com.sber.demo;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Objects;


@Tag(name="Основные запросы", description="Здесь представлены основные методы для взаимодействия")
@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final String IP = "http://82.146.61.94:";

    private static final String PORT_USERS = "8081";
    private static final String PORT_QUESTIONS = "8082";
    private static final String PORT_ARTICLES = "8083";

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";

    private String makeRequest(String url, String json, String requestMethod) {
        logger.info(url);
        logger.info(requestMethod);
        logger.info(json);

        OkHttpClient httpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*");

        if (json != null) {
            okhttp3.RequestBody formBody = okhttp3.RequestBody.create(json, MediaType.parse("application/json"));
            if (requestMethod.equals(POST))
                builder.post(formBody);
            else if (requestMethod.equals(PUT))
                builder.put(formBody);
            HttpUrl URL A =
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
    public User getUser(@PathVariable("id") int id) {
        Gson gson = new Gson();
        return gson.fromJson(makeRequest(IP + PORT_USERS + "/users/user." + id, null, GET), User.class);
    }

    @Operation(
            summary = "Создать объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PostMapping(value = "/api/users", consumes = "application/json")
    public String createUser(@RequestBody User user) {
        Gson gson = new Gson();
        return makeRequest(IP + PORT_USERS + "/users", gson.toJson(user), POST);
    }

    @Operation(
            summary = "Обновить объект пользователя",
            description = "Принимает JSON с полями объекта класса User")
    @PutMapping(value = "/api/users", consumes = "application/json")
    public String updateUser(@RequestBody User user) {
        Gson gson = new Gson();
        return makeRequest(IP + PORT_USERS + "/users", gson.toJson(user), PUT);
    }

    @Operation(
            summary = "Получить объект статьи",
            description = "Возврашает JSON с полями объекта класса Article")
    @RequestMapping(value = "/api/articles/{id}", consumes = "application/json", method = RequestMethod.GET)
    public String getArticle(@PathVariable("id") int id) {
        return makeRequest(IP + PORT_ARTICLES + "/articles/" + id, null, GET);
    }

    @Operation(
            summary = "Создать объект статьи",
            description = "Принимает JSON с полями объекта класса Article")
    @PostMapping(value = "/api/articles", consumes = "application/json")
    public String createArticle(@RequestBody String json) {
        return makeRequest(IP + PORT_ARTICLES + "/articles", json, POST);
    }

    @Operation(
            summary = "Создать объект вопроса",
            description = "Получает JSON с полями объекта класса Question")
    @PostMapping(value = "/api/questions", produces = "application/json")
    public String createQuestion(@RequestBody Question question) {
        Gson gson = new Gson();
        return makeRequest(IP + PORT_QUESTIONS + "/questions", gson.toJson(question), POST);
    }

    @Operation(
            summary = "Получить объект вопроса",
            description = "Возвращает JSON с полями объекта класса Question")
    @RequestMapping(value = "/api/questions/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public Question getQuestion(@PathVariable("id") int id) {
        Gson gson = new Gson();
        return gson.fromJson(makeRequest(IP + PORT_QUESTIONS + "/questions/" + id, null, GET), Question.class);
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
    @PutMapping(value = "/api/questions/{id}", produces = "application/json")
    public String updateQuestion(@PathVariable("id") int id, @RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/questions/" + id, json, PUT);
    }

    @Operation(
            summary = "Получить объект ответа",
            description = "Получает JSON с полями объекта класса Answer")
    @RequestMapping(value = "/api/answers/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getAnswer(@PathVariable("id") int id, @RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/answers/" + id, json, GET);
    }

    @Operation(
            summary = "Получить объект ответа",
            description = "Получает JSON с полями объекта класса Answer")
    @PutMapping(value = "/api/answers", produces = "application/json")
    public String createAnswer(@RequestBody String json) {
        return makeRequest(IP + PORT_QUESTIONS + "/answers/", json, GET);
    }
}
