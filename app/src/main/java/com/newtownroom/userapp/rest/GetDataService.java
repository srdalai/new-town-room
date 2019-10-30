package com.newtownroom.userapp.rest;

import com.newtownroom.userapp.restmodels.BookingInputModel;
import com.newtownroom.userapp.restmodels.BookingOutputModel;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.restmodels.CouponsInput;
import com.newtownroom.userapp.restmodels.CouponsOutput;
import com.newtownroom.userapp.restmodels.HotelDetailsInputModel;
import com.newtownroom.userapp.restmodels.HotelDetailsResponseModel;
import com.newtownroom.userapp.restmodels.LoginInputModel;
import com.newtownroom.userapp.restmodels.LoginResponseModel;
import com.newtownroom.userapp.restmodels.OtpInputModel;
import com.newtownroom.userapp.restmodels.OtpResponseModel;
import com.newtownroom.userapp.restmodels.SignUpInputModel;
import com.newtownroom.userapp.restmodels.SignUpResponseModel;
import com.newtownroom.userapp.restmodels.TxnStatusResponse;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("login")
    Call<LoginResponseModel> postLoginRequest(@Body LoginInputModel loginInputModel);

    @POST("validateOTP")
    Call<OtpResponseModel> validateOTP(@Body OtpInputModel otpInputModel);

    @POST("signup")
    Call<SignUpResponseModel> postSignUpRequest(@Body SignUpInputModel signUpInputModel);

    @POST("resendOTP")
    Call<LoginResponseModel> resendOTP(@Body LoginInputModel loginInputModel);

    @POST("getHotelsList")
    Call<ArrayList<HotelData>> getHotelsList();

    @POST("getHotelDetails")
    Call<HotelDetailsResponseModel> getHotelDetails(@Body HotelDetailsInputModel hotelDetailsInputModel);

    @POST("booking")
    Call<BookingOutputModel> booking(@Body BookingInputModel bookingInputModel);

    @POST("getCoupons")
    Call<CouponsOutput> getAllCoupons(@Body CouponsInput couponsInput);

    /*
    *   PayMoney API requests
    *
    * */

    @POST("chkMerchantTxnStatus")
    Call<TxnStatusResponse> checkTxnStatus(@HeaderMap Map<String, String> headers, @Query("merchantKey") String merchantKey, @Query("merchantTransactionIds") String merchantTransactionIds);
}
