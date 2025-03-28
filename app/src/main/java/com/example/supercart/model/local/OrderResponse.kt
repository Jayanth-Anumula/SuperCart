package com.example.supercart.model.local

import androidx.core.app.NotificationCompat.MessagingStyle.Message

data class OrderResponse(
    val status:Int,
    val message: Int,
    val order_id:Int
) {
}