package com.innoviussoftwaresolution.tjss.view.fragment.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskResponce {

    @SerializedName("message")
    List<getdata> taskList;

    public List<getdata> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<getdata> taskList) {
        this.taskList = taskList;
    }

    public class getdata{

        @Expose
        @SerializedName("id")
        String taskId;

        @Expose
        @SerializedName("provider_id")
        String providerId;


        @Expose
        @SerializedName("provide_name")
        String provideName;



        @Expose
        @SerializedName("user_id")
        String userId;

        @Expose
        @SerializedName("description")
        String desc;

        @Expose
        @SerializedName("amount")
        String amount;

        @Expose
        @SerializedName("task_status")
        String taskStatus;

        @Expose
        @SerializedName("created_at")
        String createDate;

        @Expose
        @SerializedName("update_at")
        String updateDate;


        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getProvideName() {
            return provideName;
        }

        public void setProvideName(String provideName) {
            this.provideName = provideName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }

}
