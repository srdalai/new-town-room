package com.newtownroom.userapp.rest;

import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.HotelDetailsInputModel;
import com.newtownroom.userapp.models.HotelDetailsResponseModel;
import com.newtownroom.userapp.models.LoginInputModel;
import com.newtownroom.userapp.models.LoginResponseModel;
import com.newtownroom.userapp.models.OtpInputModel;
import com.newtownroom.userapp.models.OtpResponseModel;
import com.newtownroom.userapp.models.SignUpInputModel;
import com.newtownroom.userapp.models.SignUpResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
