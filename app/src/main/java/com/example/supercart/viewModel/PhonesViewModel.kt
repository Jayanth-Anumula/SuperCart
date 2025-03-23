package com.example.supercart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecomerceapp.models.AndroidResponseBody
import com.example.supercart.commons.ApiResult
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.model.remote.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhonesViewModel(var repository: ProductRepository):ViewModel() {

    private var _apiPhonesResult = MutableLiveData<ApiResult<AndroidResponseBody>>()
    var apiPhoneResult:LiveData<ApiResult<AndroidResponseBody>> = _apiPhonesResult

    fun androidPhonesList(sub_category_id:String){

        try{

           viewModelScope.launch(Dispatchers.IO) {

               var response = repository.androidPhonesList(sub_category_id)

               if(response.isSuccessful && response.body() != null){
                   response.body()?.let {
                       _apiPhonesResult.postValue(ApiResult.Success(it))
                   }
               }else {
                   _apiPhonesResult.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
               }
           }
        }catch (e:Exception){
            e.printStackTrace()
            _apiPhonesResult.postValue(ApiResult.Error("Error is : $e"))
        }
    }

}

class PhonesViewModelFactory(private val repo: ProductRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhonesViewModel(repo) as T
    }
}