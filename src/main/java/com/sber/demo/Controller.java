package com.sber.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
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

    private static final String IP = "http://82.146.61.94:8081";

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

    @Operation(
            summary = "Получить объект пользователя по ID",
            description = "Возвращает JSON строку с полями объекта класса User с уникальным ID"
    )

    @RequestMapping(value = "/api/user/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody()
    public String getUser(@PathVariable("id") int id) {
        return getJsonStringFromServer(IP + "/users/user." + id);
    }

    @Operation(
            summary = "Получить объекты всех пользователей",
            description = "Возвращает JSON строку с полями объектов класса User"
    )
    @RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody()
    public String getUsers() {
        return getJsonStringFromServer(IP + "/users/");
    }

    @Operation(
            summary = "Отправить JSON файл с помощью POST",
            description = "Отправляет JSON файл"
    )
    @PostMapping(value = "/api/users/create", consumes = "application/json")
    public void createUser(@RequestBody String json) {
        String url = IP + "/users";

        logger.info(json);

        HttpURLConnection httpClient = null;
        StringBuilder result = new StringBuilder();
        try {
            httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("Content-Type", "application/json");

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
            logger.info(result + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
