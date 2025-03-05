package com.example.ip_test_task.model

import androidx.room.PrimaryKey

data class ContentEntityUI(
    @PrimaryKey val id: Long,
    val name: String,
    val time: String,
    val tags: List<String>,
    val amount: Long
)