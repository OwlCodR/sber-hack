package com.sber.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.omg.CORBA.NameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Tag(name="Основные запросы", description="Здесь представлены основные методы для взаимодействия")
@RestController
public class Controller {
    private static final String SUCCESS_STATUS = "success";

    private static final String IP = "http://localhost:8080";

    private String getJsonStringFromServer(String uri) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
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
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody()
    public String getUser(@PathVariable("id") int id) {
        return getJsonStringFromServer(IP + "/users/" + id);
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

    @PostMapping(value = "/api/questions/{json}", consumes = "application/json")
    public void setLongQuestion(@PathVariable("json") String json) {
        String url = IP + "questions/";

        HttpsURLConnection httpClient = null;
        try {
            httpClient = (HttpsURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("POST");

            httpClient.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                wr.writeBytes(json);
                wr.flush();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
