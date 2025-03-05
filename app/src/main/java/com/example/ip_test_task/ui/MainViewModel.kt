package com.example.ip_test_task.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_test_task.model.ContentEntityUI
import com.example.ip_test_task.model.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    var mutableList by mutableStateOf(listOf<ContentEntityUI>())
    var mutableRedactItem by mutableStateOf<ContentEntityUI?>(null)

    init {
        getData()
    }

    fun removeItem(id: Long) = launch {
        contentRepository.run {
            removeContentDataById(id)
            getData()
        }
    }

    fun callRedactItem(item: ContentEntityUI) {
        mutableRedactItem = item
    }

    fun redactingItem(item: ContentEntityUI) = launch {
        contentRepository.updateContentDataByItem(item)
        closeRedactItem()
        getData()
    }

    fun closeRedactItem() {
        mutableRedactItem = null
    }

    private fun getData() = launch {
        mutableList = contentRepository.getAllContentData()

        Log.i("list", "Data Content is $mutableList")
    }
}

fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(context, start, block)
}