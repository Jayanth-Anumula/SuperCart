package com.example.supercart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecomerceapp.models.LoginRequestBody
import com.example.ecomerceapp.models.LoginResponseBody
import com.example.ecomerceapp.models.RegisterUserBody
import com.example.ecomerceapp.models.RegisterUserResponse
import com.example.supercart.commons.ApiResult
import com.example.supercart.model.remote.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(val repo: UserRepository):ViewModel() {

    private val _apiResult = MutableLiveData<ApiResult<RegisterUserResponse>>()
    val apiResult:LiveData<ApiResult<RegisterUserResponse>> = _apiResult

    private val _apiLoginResult = MutableLiveData<ApiResult<LoginResponseBody>>()
    val apiLoginResult:LiveData<ApiResult<LoginResponseBody>> = _apiLoginResult



    fun registerUser(registerUserBody: RegisterUserBody) {

        viewModelScope.launch(Dispatchers.IO){

           try{
               _apiResult.postValue(ApiResult.Loading)
               val response: Response<RegisterUserResponse> = repo.registerUser(registerUserBody)

               if (response.isSuccessful) {
                   if (response.body() != null) {
                       response.body()?.let {
                           _apiResult.postValue(ApiResult.Success(it))
                       }

                   } else {
                       _apiResult.postValue(ApiResult.Error("Empty result from server"))
                   }
               } else {
                   _apiResult.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
               }

           }catch (e: Exception) {

               e.printStackTrace()
               _apiResult.postValue(ApiResult.Error("Error is : $e"))
           }


        }


    }

    fun loginUser(loginRequestBody: LoginRequestBody){

        viewModelScope.launch(Dispatchers.IO){

            try{
                _apiResult.postValue(ApiResult.Loading)
                val response: Response<LoginResponseBody> = repo.loginUser(loginRequestBody)

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.let {
                            _apiLoginResult.postValue(ApiResult.Success(it))
                        }

                    } else {
                        _apiLoginResult.postValue(ApiResult.Error("Empty result from server"))
                    }
                } else {
                    _apiLoginResult.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
                }

            }catch (e: Exception) {

                e.printStackTrace()
                _apiLoginResult.postValue(ApiResult.Error("Error is : $e"))
            }


        }

    }

}

class UserViewModelFactory(private val repo: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repo) as T
    }
}