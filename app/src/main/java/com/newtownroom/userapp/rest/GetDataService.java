package com.newtownroom.userapp.rest;

import com.newtownroom.userapp.models.GstModel;
import com.newtownroom.userapp.restmodels.AllBookingsInput;
import com.newtownroom.userapp.restmodels.AllBookingsResponse;
import com.newtownroom.userapp.restmodels.BookingDetailsResponses;
import com.newtownroom.userapp.restmodels.BookingInput;
import com.newtownroom.userapp.restmodels.BookingResponse;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.restmodels.CancelBookingResponse;
import com.newtownroom.userapp.restmodels.CheckAvailInput;
import com.newtownroom.userapp.restmodels.CheckAvailResponse;
import com.newtownroom.userapp.restmodels.CouponsInput;
import com.newtownroom.userapp.restmodels.CouponsResponse;
import com.newtownroom.userapp.restmodels.DeleteGstInput;
import com.newtownroom.userapp.restmodels.DeleteGstResponse;
import com.newtownroom.userapp.restmodels.GstResponse;
import com.newtownroom.userapp.restmodels.HomeResponse;
import com.newtownroom.userapp.restmodels.HotelDetailsInput;
import com.newtownroom.userapp.restmodels.HotelDetailsResponse;
import com.newtownroom.userapp.restmodels.LoginInput;
import com.newtownroom.userapp.restmodels.LoginResponse;
import com.newtownroom.userapp.restmodels.OtpInput;
import com.newtownroom.userapp.restmodels.OtpResponse;
import com.newtownroom.userapp.restmodels.PaymentInput;
import com.newtownroom.userapp.restmodels.PaymentResponse;
import com.newtownroom.userapp.restmodels.SignUpInput;
import com.newtownroom.userapp.restmodels.SignUpResponse;
import com.newtownroom.userapp.restmodels.SingleBookingID;
import com.newtownroom.userapp.restmodels.SingleUserID;
import com.newtownroom.userapp.restmodels.TxnStatusResponse;
import com.newtownroom.userapp.restmodels.UpdateUserInput;
import com.newtownroom.userapp.restmodels.UpdateUserResponse;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("login")
    Call<LoginResponse> postLoginRequest(@Body LoginInput loginInput);

    @POST("validateOTP")
    Call<OtpResponse> validateOTP(@Body OtpInput otpInput);

    @POST("signup")
    Call<SignUpResponse> postSignUpRequest(@Body SignUpInput signUpInput);

    @POST("resendOTP")
    Call<LoginResponse> resendOTP(@Body LoginInput loginInput);

    @POST("updateUser")
    Call<UpdateUserResponse> updateUser(@Body UpdateUserInput updateUserInput);

    @POST("home")
    Call<HomeResponse> getHomeData();

    @POST("getHotelsList")
    Call<ArrayList<HotelData>> getHotelsList();

    @POST("getHotelDetails")
    Call<HotelDetailsResponse> getHotelDetails(@Body HotelDetailsInput hotelDetailsInput);

    @POST("checkAvailability")
    Call<CheckAvailResponse> checkAvailability(@Body CheckAvailInput checkAvailInput);

    @POST("booking")
    Call<BookingResponse> booking(@Body BookingInput bookingInput);

    @POST("getCoupons")
    Call<CouponsResponse> getAllCoupons(@Body CouponsInput couponsInput);

    @POST("getBookingDetails")
    Call<BookingDetailsResponses> getBookingDetails(@Body SingleBookingID singleBookingID);

    @POST("getBookingList")
    Call<AllBookingsResponse> getBookingList(@Body AllBookingsInput allBookingsInput);

    @POST("deleteBooking")
    Call<CancelBookingResponse> deleteBooking(@Body SingleBookingID singleBookingID);

    @POST("getUserGst")
    Call<GstModel> getUserGst(@Body SingleUserID singleUserID);

    @POST("createUserGst")
    Call<GstResponse> createUserGst(@Body GstModel gstModel);

    @POST("updateUserGst")
    Call<GstResponse> updateUserGst(@Body GstModel gstModel);

    @POST("deleteUserGst")
    Call<DeleteGstResponse> deleteUserGst(@Body DeleteGstInput deleteGstInput);

    @POST("paymentProcess")
    Call<PaymentResponse> paymentProcess(@Body PaymentInput paymentInput);

    /*
    *   PayMoney API requests
    *
    * */

    @POST("chkMerchantTxnStatus")
    Call<TxnStatusResponse> checkTxnStatus(@HeaderMap Map<String, String> headers, @Query("merchantKey") String merchantKey, @Query("merchantTransactionIds") String merchantTransactionIds);
}
