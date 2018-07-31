package com.twixttechnologies.tjss.model.network.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sony Raj on 07-08-17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "subscriptionPlan",
        "userid",
        "fname",
        "lname",
        "email",
        "isdcode",
        "phone",
        "profileImage",
        "primaryCircle",
        "accesstoken",
        "refreshtoken",
        "mapOptions",
        "securityQuestion",
        "message"
})
public class SignUpAndLoginResponse {

    @JsonProperty("subscriptionPlan")
    public String subscriptionPlan;
    @JsonProperty("userid")
    public String userid;
    @JsonProperty("fname")
    public String fname;

    @JsonProperty("lname")
    public String lname;
    @JsonProperty("email")
    public String email;
    @JsonProperty("isdcode")
    public String isdcode;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("profileImage")
    public String profileImage;
    @JsonProperty("primaryCircle")
    public String primaryCircle;
    @JsonProperty("accesstoken")
    public String accesstoken;
    @JsonProperty("refreshtoken")
    public String refreshtoken;
    @JsonProperty("mapOptions")
    public String mapOptions;
    @JsonProperty("message")
    public String message;
    @JsonProperty("securityQuestion")
    public String securityQuestion;

    @JsonProperty("serviceProvider")
    public String serviceProvider;
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
