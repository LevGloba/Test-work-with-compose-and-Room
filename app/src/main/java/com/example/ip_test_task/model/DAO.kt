package com.example.ip_test_task.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {

    @Query("SELECT * FROM item ")
    fun getAllContents(): List<DbContentEntity>

    @Update
    fun updateContent(item: DbContentEntity)

    @Query("DELETE FROM item  WHERE id = :id")
    fun deleteContent(id: Long)

}