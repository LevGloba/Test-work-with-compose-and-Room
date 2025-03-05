package com.example.ip_test_task.data_layer.data_source

import android.content.Context
import android.util.Log
import androidx.room.Room.databaseBuilder
import com.example.ip_test_task.model.AppDataBaseAbstract
import com.example.ip_test_task.model.CreateRoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateRoomDatabaseImpl @Inject constructor(@ApplicationContext private val applicationContext: Context): CreateRoomDatabase {

    private val appDatabase: AppDataBaseAbstract = synchronized(AppDataBaseAbstract::class) {
        databaseBuilder(applicationContext, AppDataBaseAbstract::class.java, "contents.db")
            .allowMainThreadQueries()
            .createFromAsset("data.db")
            .build().apply {
                openHelper.writableDatabase
            }
    }

    override fun initDataBase() {
        appDatabase.run {
            getDAO().getAllContents()
            if (isOpen)
                Log.i("list", "create DataBase ${getDAO().getAllContents()}")
        }
    }

    override fun returnAppDataBaseAbstract(): AppDataBaseAbstract = appDatabase
}