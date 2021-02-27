package com.sber.demo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Длинный вопрос")
public class LongQuestion extends ShortQuestion {
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
