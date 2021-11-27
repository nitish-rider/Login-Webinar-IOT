package `in`.iot.lab.loginwebinar.api

import `in`.iot.lab.loginwebinar.Models.Login
import `in`.iot.lab.loginwebinar.Models.Register
import `in`.iot.lab.loginwebinar.Models.ResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("register")
    suspend fun register(
        @Body register: Register
    ):Response<ResponseModel>

    @POST("signin")
    suspend fun signIn(
        @Body login: Login
    ):Response<ResponseModel>
}