package com.example.supercart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecomerceapp.models.AndroidResponseBody
import com.example.supercart.commons.ApiResult
import com.example.supercart.model.local.CategoriesResponse
<<<<<<< HEAD
import com.example.supercart.model.local.ProductDetails
=======
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02
import com.example.supercart.model.local.subCategoryResponse
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.model.remote.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhonesViewModel(var repository: ProductRepository):ViewModel() {

    private var _apiPhonesResult = MutableLiveData<ApiResult<AndroidResponseBody>>()
    var apiPhoneResult:LiveData<ApiResult<AndroidResponseBody>> = _apiPhonesResult

    private var _apiCategoriesResult = MutableLiveData<ApiResult<CategoriesResponse>>()
    var apiCategoriesResult:LiveData<ApiResult<CategoriesResponse>> = _apiCategoriesResult

    private var _apiSubCategoriesResult = MutableLiveData<ApiResult<subCategoryResponse>>()
    var apiSubCategoriesResult:LiveData<ApiResult<subCategoryResponse>> = _apiSubCategoriesResult

<<<<<<< HEAD
    private var _apiProductDetailsResult = MutableLiveData<ProductDetails>()
    var apiProductDetailsResult:LiveData<ProductDetails> = _apiProductDetailsResult

    private var _error = MutableLiveData<String>()
    var error:LiveData<String> = _error

=======
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02

    fun categoriesList(){
        try{

           viewModelScope.launch(Dispatchers.IO) {

               val response = repository.categoriesList()
               if(response.isSuccessful && response.body() != null){
                   response.body()?.let {
                       _apiCategoriesResult.postValue(ApiResult.Success(it))
                   }
               }else {
                   _apiCategoriesResult.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
               }
           }

        }catch (e:Exception){

            e.printStackTrace()
            _apiCategoriesResult.postValue(ApiResult.Error("Error is : $e"))

        }
    }

    fun subCategoriesList(category_id:String){

        try{

            viewModelScope.launch(Dispatchers.IO) {

                var response = repository.subCategoriesList(category_id)

                if(response.isSuccessful && response.body() != null){
                    response.body()?.let {
                        _apiSubCategoriesResult.postValue(ApiResult.Success(it))
                    }
                }else {
                    _apiSubCategoriesResult.postValue(ApiResult.Error("Error is: ${response.errorBody()?.string()}"))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _apiSubCategoriesResult.postValue(ApiResult.Error("Error is : $e"))
        }
    }

<<<<<<< HEAD
    fun getProductDetails(product_id:String){

        viewModelScope.launch {

            try{
                val res = repository.getProductDetails(product_id)
                if(res.isSuccessful && res.body() != null){
                    _apiProductDetailsResult.postValue(res.body())
                }
            }catch (e:Exception){
                _error.postValue(e.printStackTrace().toString())
            }
        }

    }

=======
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02







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