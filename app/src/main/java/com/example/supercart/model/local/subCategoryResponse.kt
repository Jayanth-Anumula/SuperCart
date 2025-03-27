package com.example.supercart.model.local

data class subCategoryResponse(

    val status : Int,
    val message : String,
    val subcategories:List<subcategories>
) {
}