package com.example.ip_test_task.model

interface ContentRepository {

    suspend fun getAllContentData(): List<ContentEntityUI>

    suspend fun updateContentDataByItem(item: ContentEntityUI)

    suspend fun removeContentDataById(id: Long)
}