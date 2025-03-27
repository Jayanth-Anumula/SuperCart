package com.example.supercart.model.local

data class CategoriesResponse(

    val status : Int,
    val message : String,
    val categories:List<Categories>

) {
}