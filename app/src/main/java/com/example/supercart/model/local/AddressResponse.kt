package com.example.supercart.model.local

data class AddressResponse(

    val status:Int,
    val message:String,
    val addresses : List<AddressData>
) {
}