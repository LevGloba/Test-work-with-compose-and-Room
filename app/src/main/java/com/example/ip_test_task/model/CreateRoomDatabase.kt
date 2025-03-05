package com.example.ip_test_task.model

interface CreateRoomDatabase {

    fun initDataBase()

    fun returnAppDataBaseAbstract(): AppDataBaseAbstract
}