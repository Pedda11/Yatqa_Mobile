package com.example.yatqa_mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yatqa_mobile.data.datamodels.Login

@Database(entities = [Login::class], version = 1)
abstract class LoginDatabase(): RoomDatabase() {
    abstract val loginDatabaseDao: LoginDatabaseDao
}

private lateinit var INSTANCE : LoginDatabase

fun getDatabase(context: Context): LoginDatabase{
    synchronized(LoginDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                LoginDatabase::class.java,
                "login_database_deluxe3000"
            )
                .build()
        }
    }
    return INSTANCE
}