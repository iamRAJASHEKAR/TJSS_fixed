package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 31-10-17.
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
        "securityAnswer",
        "securityPin",
        "id",
        "question"
})
public class SelectedSecurityQuestionResponse {

    @JsonProperty("id")
    public int id;
    @JsonProperty("securityAnswer")
    public String securityAnswer;
    @JsonProperty("securityPin")
    public int securityPin;
    @JsonProperty("question")
    public String question;
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