package com.twixttechnologies.tjss.model.internal;

/**
 * @author Sony Raj on 04-10-17.
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
        "groupName",
        "id",
        "content",
        "created_at",
        "profile_image",
        "group_image",
        "singleUser",
        "profileName",
        "message_type",
        "unreadCount"
})
public class ChatListItem {

    @JsonProperty("groupName")
    public String groupName;
    @JsonProperty("id")
    public String id;
    @JsonProperty("content")
    public String content;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("profile_image")
    public String profileImage;
    @JsonProperty("group_image")
    public String groupImage;
    @JsonProperty("singleUser")
    public Integer singleUser;
    @JsonProperty("profileName")
    public String profileName;
    @JsonProperty("message_type")
    public String messageType;
    @JsonProperty("unreadCount")
    public int unreadCount;
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