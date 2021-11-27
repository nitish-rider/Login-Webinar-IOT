package `in`.iot.lab.loginwebinar.api

import `in`.iot.lab.loginwebinar.util.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:LoginApi  by lazy{
        retrofit.create(LoginApi::class.java)
    }
}