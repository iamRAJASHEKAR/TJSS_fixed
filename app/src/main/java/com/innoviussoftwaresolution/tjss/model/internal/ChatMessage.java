package com.innoviussoftwaresolution.tjss.model.internal;

/**
 * @author Sony Raj on 03-10-17.
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
        "content",
        "fromId",
        "groupId",
        "senderName",
        "senderImage",
        "read_status",
        "created_at",
        "message_type",
        "id",
        "thumbNail",
        "deleteStatus",
        "key"
})
public class ChatMessage {

    public String content;
    @JsonProperty("fromId")
    public String fromId;
    @JsonProperty("groupId")
    public String groupId;
    @JsonProperty("senderName")
    public String senderName;
    @JsonProperty("senderImage")
    public String senderImage;
    @JsonProperty("read_status")
    public long readStatus;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("message_type")
    public String messageType;
    @JsonProperty("id")
    public int id;
    @JsonProperty("thumbNail")
    public String thumbNail;
    @JsonProperty("deleteStatus")
    public int deleteStatus;
    @JsonProperty("key")
    public String key;

    @JsonIgnore
    public int progress = 0;

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