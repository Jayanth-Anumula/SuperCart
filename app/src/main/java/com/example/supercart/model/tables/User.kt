package com.example.supercart.model.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id: Long = 0,
    val email_id: String,
    val password:String,
    val isLogin:Boolean
)
