package com.fmi.hotelreviewboard.model.dto;

public class ReviewServiceModel {
    private String id;
    private Integer score;

    public ReviewServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
