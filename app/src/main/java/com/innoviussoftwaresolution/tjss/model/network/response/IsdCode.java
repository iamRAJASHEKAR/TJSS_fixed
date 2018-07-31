package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 04-08-17.
 */

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country_name",
        "country_code",
        "isd_code"
})
public class IsdCode {

    @JsonProperty("country_name")
    public String countryName;
    @JsonProperty("country_code")
    public String countryCode;
    @JsonProperty("isd_code")
    public String isdCode;
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

    @Override
    public String toString() {
        return MessageFormat.format("({0}) +{1}", countryName, isdCode);
    }
}