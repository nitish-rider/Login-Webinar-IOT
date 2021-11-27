package `in`.iot.lab.loginwebinar.viewModel

import `in`.iot.lab.loginwebinar.Models.Login
import `in`.iot.lab.loginwebinar.Models.Register
import `in`.iot.lab.loginwebinar.Models.ResponseModel
import `in`.iot.lab.loginwebinar.repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository):ViewModel() {
    val myRegisterResponse: MutableLiveData<Response<ResponseModel>> = MutableLiveData()
    val myLoginResponse:MutableLiveData<Response<ResponseModel>> = MutableLiveData()

    fun register(register: Register){
        viewModelScope.launch {
            val response=repository.register(register)
            myRegisterResponse.value=response
        }
    }

    fun signIn(login: Login){
        viewModelScope.launch{
            val response=repository.sigIn(login)
            myLoginResponse.value=response
        }
    }
}