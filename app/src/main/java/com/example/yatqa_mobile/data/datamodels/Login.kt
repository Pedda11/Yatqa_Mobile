package com.example.yatqa_mobile.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database-class
 */
@Entity
data class Login(

    //Unique-ID
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val ip: String,
    val qPort: Int = 10011,
    val port: Int?,
    val userName: String?,
    val userPassword: String?,
    val listName: String?
)
