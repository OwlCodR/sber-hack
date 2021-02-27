package com.sber.demo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Короткий вопрос")
public class Question {
    private int id;
    private int rating;
    private int priority;
    private String header;
    private String tags;
    private String content;
    private String createdBy;
    private String updatedAt;
    private String createdAt;
    private boolean isSolved;

    public Question() {
    }

    public Question(int id, int rating, int priority, String header, String tags, String content, String createdBy, String updatedAt, String createdAt, boolean isSolved) {
        this.id = id;
        this.rating = rating;
        this.priority = priority;
        this.header = header;
        this.tags = tags;
        this.content = content;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.isSolved = isSolved;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
