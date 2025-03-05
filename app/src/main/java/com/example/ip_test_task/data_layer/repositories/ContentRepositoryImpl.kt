package com.example.ip_test_task.data_layer.repositories

import com.example.ip_test_task.model.ContentEntityUI
import com.example.ip_test_task.model.ContentRepository
import com.example.ip_test_task.model.CreateRoomDatabase
import com.example.ip_test_task.model.DAO
import com.example.ip_test_task.model.DbContentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val createRoomDatabase: CreateRoomDatabase) :
    ContentRepository {

    override suspend fun getAllContentData(): List<ContentEntityUI> {
        return withContext(Dispatchers.IO) {
            return@withContext getDao().getAllContents().map {
                it.convert()
            }
        }
    }

    override suspend fun updateContentDataByItem(item: ContentEntityUI) {
        withContext(Dispatchers.IO) {
            getDao().updateContent(item.convert())
        }
    }

    override suspend fun removeContentDataById(id: Long) {
        withContext(Dispatchers.IO) {
            getDao().deleteContent(id)
        }
    }

    private fun getDao(): DAO {
        createRoomDatabase.run {
            val appDataBase = returnAppDataBaseAbstract()
            return if (appDataBase.isOpen)
                appDataBase.getDAO()
            else {
                initDataBase()
                appDataBase.getDAO()
            }
        }
    }
}

fun ContentEntityUI.convert(): DbContentEntity =
    DbContentEntity(
        id = id,
        time = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(time)?.time ?: 0,
        name = name,
        tags = tags.toString(),
        amount = amount
    )

fun DbContentEntity.convert(): ContentEntityUI =
    ContentEntityUI(
        id = id,
        time = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date(time)),
        name = name,
        tags = tags.run {
            val list = replace(Regex("(\\[|]|\\s)"), "")
            if (list.isNotEmpty())
                list.split(",")
            else
                emptyList()
        },
        amount = amount
    )
