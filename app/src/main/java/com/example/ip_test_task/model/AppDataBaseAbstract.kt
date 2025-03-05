package com.example.ip_test_task.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        DbContentEntity::class
    ],
    exportSchema = false
)
abstract class AppDataBaseAbstract: RoomDatabase () {

    abstract fun getDAO(): DAO
}