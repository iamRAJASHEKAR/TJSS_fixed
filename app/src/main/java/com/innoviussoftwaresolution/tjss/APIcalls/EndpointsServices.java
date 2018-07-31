package com.innoviussoftwaresolution.tjss.APIcalls;

import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.CheckInModel;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.DeleteHistory;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.CircleDetailsModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactResponseModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactsModel;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.SendRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.AcceptRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.GetCategoryRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestsRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.StatusResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.SubmitResponce;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderCategory;
import com.innoviussoftwaresolution.tjss.view.fragment.invoice.TaskResponce;
import com.innoviussoftwaresolution.tjss.view.fragment.signup.SignUpOtp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 3/29/2018.
 */

public interface EndpointsServices {

    @POST(APIconstants.ADD_HISTORY)
    Call<CheckInModel> addCheckInHistory(@Body CheckInModel checkInModel);


    @POST(APIconstants.DELETE_HISTORY)
    Call<CheckInHistory> deleteCheckIn(@Body DeleteHistory deleteHistory);

    @POST(APIconstants.SIGNUP_OTP)
    Call<SignUpOtp> signUpOtp(@Body SignUpOtp signUpOtpSend);

    @POST(APIconstants.SEARCH_SERVICE_PROVIDER)
    Call<NearByServiceProviderResponseModel> getNearByProviders(@Body NearByServiceProviderRequestModel providerRequestModel);

    @POST(APIconstants.CHECK_STATUS)
    Call<CheckStatusResponseModel> checkStatus(@Body CheckStatusRequestModel requestModel);

    @POST(APIconstants.GET_ALL_REQUEST)
    Call<ProviderGetAllRequestResponseModel> getAllRequest(@Body ProviderGetAllRequestsRequestModel getAllRequestsRequestModel);

    @POST(APIconstants.ACCEPT_REQUEST)
    Call<StatusResponseModel> requestStatus(@Body AcceptRequestModel acceptRequestModel);

    @POST(APIconstants.GET_ALL_TASK)
    Call<ProviderGetAllRequestResponseModel> getAllTask(@Body ProviderGetAllRequestsRequestModel requestsRequestModel);

    @POST(APIconstants.GET_ALL_CONTACTS)
    Call<ContactResponseModel> postAllContacts(@Body ContactsModel requestModel);

    @POST(APIconstants.SEND_REQUEST)
    Call<SendRequestResponseModel> sendRequest(@Body CircleDetailsModel model);


    @FormUrlEncoded
    @POST(APIconstants.SET_TASK_AMDES)
    Call<SubmitResponce> settaskdescandamount(@Field("provider_id") String providerId,@Field("user_id") String userId, @Field("description") String desc, @Field("amount") String amount);


    @FormUrlEncoded
    @POST(APIconstants.SET_TASK_LIST_REVIEW)
    Call<TaskResponce> setrevietasklist(@Field("task_id") String userId, @Field("task_status") String status);


    @FormUrlEncoded
    @POST(APIconstants.GET_INVOICE_LIST)
    Call<TaskResponce> getinvoicelist(@Field("user_id") String userId);


    @POST(APIconstants.GET_SP_CATEGORY)
    Call<ServiceProviderCategory> getAllCategory(@Body GetCategoryRequestModel requestModel);


}
