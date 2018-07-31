package com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory;


import android.provider.CallLog;

import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.CircleDetailsModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactRequestModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactResponseModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactsModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.SendRequestResponseModel;

import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.AcceptRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestsRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.StatusResponseModel;

import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.SubmitResponce;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProvider;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.view.fragment.invoice.TaskResponce;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProvider;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import com.innoviussoftwaresolution.tjss.view.fragment.signup.SignUpOtp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by admin on 3/29/2018.
 */

public interface EndpointsServices {

    @POST(CheckInHistoryConstants.ADD_HISTORY)
    Call<CheckInModel> addCheckInHistory(@Body CheckInModel checkInModel);


    @POST(CheckInHistoryConstants.DELETE_HISTORY)
    Call<CheckInHistory> deleteCheckIn(@Body DeleteHistory deleteHistory);

    @POST(CheckInHistoryConstants.SIGNUP_OTP)
    Call<SignUpOtp> signUpOtp(@Body SignUpOtp signUpOtpSend);

    @POST(CheckInHistoryConstants.SEARCH_SERVICE_PROVIDER)

    Call<NearByServiceProviderResponseModel> getNearByProviders(@Body NearByServiceProviderRequestModel providerRequestModel);

    @POST(CheckInHistoryConstants.CHECK_STATUS)
    Call<CheckStatusResponseModel> checkStatus(@Body CheckStatusRequestModel requestModel);

    @POST(CheckInHistoryConstants.GET_ALL_REQUEST)
    Call<ProviderGetAllRequestResponseModel> getAllRequest(@Body ProviderGetAllRequestsRequestModel getAllRequestsRequestModel);

    @POST(CheckInHistoryConstants.ACCEPT_REQUEST)
    Call<StatusResponseModel> requestStatus(@Body AcceptRequestModel acceptRequestModel);

    @POST(CheckInHistoryConstants.GET_ALL_TASK)
    Call<ProviderGetAllRequestResponseModel> getAllTask(@Body ProviderGetAllRequestsRequestModel requestsRequestModel);

    @POST(CheckInHistoryConstants.GET_ALL_CONTACTS)
    Call<ContactResponseModel> postAllContacts(@Body ContactsModel requestModel);

    @POST(CheckInHistoryConstants.SEND_REQUEST)
    Call<SendRequestResponseModel> sendRequest(@Body CircleDetailsModel model);


    @FormUrlEncoded
    @POST(CheckInHistoryConstants.SET_TASK_AMDES)
    Call<SubmitResponce> settaskdescandamount(@Field("provider_id") String providerId,@Field("user_id") String userId, @Field("description") String desc, @Field("amount") String amount);


    @FormUrlEncoded
    @POST(CheckInHistoryConstants.SET_TASK_LIST_REVIEW)
    Call<TaskResponce> setrevietasklist(@Field("task_id") String userId, @Field("task_status") String status);


/*
    @FormUrlEncoded
    @POST(CheckInHistoryConstants.GET_INVOICE_LIST)
    Call<TaskResponce> getinvoicelist(@Field("user_id") String userId);

    Call<NearByServiceProviderResponseModel> getNearByProviders (@Body NearByServiceProviderRequestModel providerRequestModel);

    @POST(CheckInHistoryConstants.CHECK_STATUS)
    Call<CheckStatusResponseModel> checkStatus (@Body CheckStatusRequestModel requestModel);

    @POST(CheckInHistoryConstants.GET_ALL_REQUEST)
    Call<ProviderGetAllRequestResponseModel> getAllRequest (@Body ProviderGetAllRequestsRequestModel getAllRequestsRequestModel);

    @POST(CheckInHistoryConstants.ACCEPT_REQUEST)
    Call<StatusResponseModel> requestStatus (@Body AcceptRequestModel acceptRequestModel);

    @POST(CheckInHistoryConstants.GET_ALL_TASK)
    Call<ProviderGetAllRequestResponseModel> getAllTask (@Body ProviderGetAllRequestsRequestModel requestsRequestModel);



*/




}
