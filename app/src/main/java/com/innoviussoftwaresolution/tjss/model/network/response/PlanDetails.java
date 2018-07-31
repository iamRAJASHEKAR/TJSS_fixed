package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 05-08-17.
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
        "plan_name",
        "plan_description",
        "no_of_safety_circles",
        "acitive_safety_circles",
        "location_history_duration",
        "favorite_places",
        "min_points_required",
        "safetytip_name",
        "help_alerts",
        "emergency_call_out_request",
        "check_in",
        "secure_messaging",
        "incident_alert_notification",
        "safety_pal",
        "stripePlan",
        "memberCount",
        "planPrice"
})
public class PlanDetails {

    @JsonProperty("plan_name")
    public String planName;

    @JsonProperty("plan_description")
    public String planDescription;

    @JsonProperty("no_of_safety_circles")
    public String noOfSafetyCircles;

    @JsonProperty("acitive_safety_circles")
    public String acitiveSafetyCircles;

    @JsonProperty("location_history_duration")
    public String locationHistoryDuration;

    @JsonProperty("favorite_places")
    public String favoritePlaces;

    @JsonProperty("min_points_required")
    public String minPointsRequired;

    @JsonProperty("safetytip_name")
    public String safetytipName;

    @JsonProperty("help_alerts")
    public String helpAlerts;

    @JsonProperty("emergency_call_out_request")
    public String emergencyCallOutRequest;

    @JsonProperty("check_in")
    public String checkIn;

    @JsonProperty("secure_messaging")
    public String secureMessaging;

    @JsonProperty("incident_alert_notification")
    public String incidentAlertNotification;

    @JsonProperty("safety_pal")
    public String safetyPal;

    @JsonProperty("stripePlan")
    public String stripePlan;

    @JsonProperty("memberCount")
    public String memberCount;

    @JsonProperty("planPrice")
    public Integer planPrice;

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