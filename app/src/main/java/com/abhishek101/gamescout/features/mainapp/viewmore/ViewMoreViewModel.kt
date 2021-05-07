package com.abhishek101.gamescout.features.mainapp.viewmore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.EmptyList
import com.abhishek101.core.models.ListData
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.ListType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewMoreViewModel(private val gameRepository: GameRepository) : ViewModel() {
    val listData = mutableStateOf<ListData>(EmptyList)

    fun getGameList(listType: ListType) {
        viewModelScope.launch {
            gameRepository.getListDataForType(listType).collect {
                listData.value = it
            }
        }
    }
}
