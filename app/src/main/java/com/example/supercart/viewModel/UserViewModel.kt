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
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.AddressResponse
import com.example.supercart.model.local.OrderRequestBody
import com.example.supercart.model.local.OrdersResponse
import com.example.supercart.model.remote.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(val repo: UserRepository):ViewModel() {

    private val _apiResult = MutableLiveData<ApiResult<RegisterUserResponse>>()
    val apiResult:LiveData<ApiResult<RegisterUserResponse>> = _apiResult

    private val _apiLoginResult = MutableLiveData<ApiResult<LoginResponseBody>>()
    val apiLoginResult:LiveData<ApiResult<LoginResponseBody>> = _apiLoginResult

    private val _addressList = MutableLiveData<AddressResponse>()
    val addressList:LiveData<AddressResponse> = _addressList

    private val _apiPostAddress = MutableLiveData<ApiResult<RegisterUserResponse>>()
    val apiPostAddress:LiveData<ApiResult<RegisterUserResponse>> = _apiPostAddress

    private val _error = MutableLiveData<String>()
    val error:LiveData<String> = _error

    private val _postOrder = MutableLiveData<String>()
    val postOrder:LiveData<String> = _postOrder

    private val _ordersList = MutableLiveData<OrdersResponse>()
    val ordersList:LiveData<OrdersResponse> = _ordersList



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

    fun getAllAddress(userId:String){

        viewModelScope.launch(Dispatchers.IO) {

            try{
                val res = repo.getAllAddress(userId)
                _addressList.postValue(res.body())

            }catch (e:Exception){
                e.printStackTrace()
              _error.postValue(e.toString())
            }
        }
    }

    fun postAddress(address: Address){

        viewModelScope.launch(Dispatchers.IO) {

            val response = repo.postAddress(address)

            if (response.isSuccessful) {
                if (response.body()!=null) {
                    response.body()?.let {
                        _apiPostAddress.postValue(ApiResult.Success(it))
                    }

                } else {
                    _apiPostAddress.postValue(ApiResult.Error("Empty result from server"))
                }
            } else {
                _apiPostAddress.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
            }
        }
    }

    fun postOrder(orderRequestBody: OrderRequestBody){

        viewModelScope.launch(Dispatchers.IO) {

            try{

                val response = repo.postOrders(orderRequestBody)

                if(response.isSuccessful && response.body() != null && response.body()!!.order_id > 1){

                    _postOrder.postValue("Order is placed with id : ${response.body()!!.order_id}")
                }else {
                    _error.postValue("Order not placed")
                }

            }catch (e:Exception){

                _error.postValue("Order not placed : ${e.printStackTrace()}")

            }
        }
    }

    fun getAllOrders(userId: String){

        viewModelScope.launch {
            try {
                val res = repo.getAllOrders(userId)
                if(res.isSuccessful && res.body() != null){
                    _ordersList.postValue(res.body())
                }else{
                    _error.postValue("response is null")
                }
            }catch (e:Exception){
                _error.postValue(e.printStackTrace().toString())
            }
        }
    }



}

class UserViewModelFactory(private val repo: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repo) as T
    }
}