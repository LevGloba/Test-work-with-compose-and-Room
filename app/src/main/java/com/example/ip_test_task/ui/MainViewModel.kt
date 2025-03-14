package com.example.ip_test_task.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_test_task.model.ContentEntityUI
import com.example.ip_test_task.model.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

data class MainUI(
    val list: List<ContentEntityUI> = emptyList(),
    val contentIsRedacting: ContentEntityUI? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow(MainUI())

    val stateUi = mutableStateFlow.asStateFlow()

    init {
        getData()
    }

    fun removeItem(id: Long) = launch {
        contentRepository.run {
            removeContentDataById(id)
            getData()
        }
    }

    fun callRedactItem(item: ContentEntityUI) = launch {
        mutableStateFlow.run {
            emit(
                value.copy(
                    contentIsRedacting = item
                )
            )
        }
    }

    fun redactingItem(item: ContentEntityUI) = launch {
        contentRepository.updateContentDataByItem(item)
        closeRedactItem()
        getData()
    }

    fun closeRedactItem() = launch {
        mutableStateFlow.run {
            emit(
                value.copy(
                    contentIsRedacting = null
                )
            )
        }
    }

    private fun getData() = launch {
        mutableStateFlow.run {
            emit(
                value.copy(
                    list = contentRepository.getAllContentData()
                )
            )

            Log.i("list", "Data Content is ${value.list}")
        }
    }
}


fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(context, start, block)
}