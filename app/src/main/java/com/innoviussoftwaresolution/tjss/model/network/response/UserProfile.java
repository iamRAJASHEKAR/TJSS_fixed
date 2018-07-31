package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 09-08-17.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fname",
        "lname",
        "profile_image",
        "email",
        "user_phone",
        "isdcode"
})
public class UserProfile {

    @JsonProperty("id")
    public String id;
    @JsonProperty("fname")
    public String fname;
    @JsonProperty("lname")
    public String lname;
    @JsonProperty("profile_image")
    public String profileImage;
    @JsonProperty("email")
    public String email;
    @JsonProperty("user_phone")
    public String userPhone;
    @JsonProperty("isdcode")
    public String isdcode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}