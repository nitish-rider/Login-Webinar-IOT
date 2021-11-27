package `in`.iot.lab.loginwebinar.repository

import `in`.iot.lab.loginwebinar.Models.Login
import `in`.iot.lab.loginwebinar.Models.Register
import `in`.iot.lab.loginwebinar.Models.ResponseModel
import `in`.iot.lab.loginwebinar.api.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun register(register: Register): Response<ResponseModel> {
        return RetrofitInstance.api.register(register)
    }

    suspend fun sigIn(login: Login):Response<ResponseModel>{
        return RetrofitInstance.api.signIn(login)
    }
}