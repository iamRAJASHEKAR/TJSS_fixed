package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import com.google.gson.annotations.SerializedName;

public class CircleDetailsModel {

    @SerializedName("id")
    private String id;

    @SerializedName("fname")
    private String name;

    @SerializedName("name")
    private String circleName;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public CircleDetailsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
