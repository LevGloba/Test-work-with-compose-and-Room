package com.example.ip_test_task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class DbContentEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val time: Long,
    val tags: String,
    val amount: Long
)
