package `in`.iot.lab.loginwebinar.Models

data class Register(
    val firstname:String,
    val lastname:String,
    val email:String,
    val password:String,
    val confirmpassword:String
)
