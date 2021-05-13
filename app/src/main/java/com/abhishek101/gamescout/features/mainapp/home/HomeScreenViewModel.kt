package com.abhishek101.gamescout.features.mainapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.EmptyList
import com.abhishek101.core.models.ListData
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.LibraryRepository
import com.abhishek101.core.repositories.ListType
import com.abhishek101.core.repositories.ListType.COMING_SOON
import com.abhishek101.core.repositories.ListType.MOST_HYPED
import com.abhishek101.core.repositories.ListType.RECENT
import com.abhishek101.core.repositories.ListType.SHOWCASE
import com.abhishek101.core.repositories.ListType.TOP_RATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val gameRepository: GameRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {
    val showcaseList = mutableStateOf<ListData>(EmptyList)
    val topRatedList = mutableStateOf<ListData>(EmptyList)
    val comingSoonList = mutableStateOf<ListData>(EmptyList)
    val recentList = mutableStateOf<ListData>(EmptyList)
    val mostHypedList = mutableStateOf<ListData>(EmptyList)

    init {
        getInitialLists()
        getSecondaryLists()
    }

    private fun getSecondaryLists() {
        viewModelScope.launch {
            delay(1000) // To prevent hitting the image endpoint all at once.
            val topRatedFlow = getListData(TOP_RATED)
            val recentFlow = getListData(RECENT)
            val mostHypedFlow = getListData(MOST_HYPED)
            combine(topRatedFlow, recentFlow, mostHypedFlow) { result1, result2, result3 ->
                topRatedList.value = result1
                recentList.value = result2
                mostHypedList.value = result3
            }.collect()
        }
    }

    private fun getInitialLists() {
        viewModelScope.launch {
            val showcaseFlow = getListData(SHOWCASE)
            val comingSoonFlow = getListData(COMING_SOON)
            showcaseFlow.combine(comingSoonFlow) { result1, result2 ->
                showcaseList.value = result1
                comingSoonList.value = result2
            }.collect()
        }
    }

    fun isAnyDataPresent(): Boolean {
        return showcaseList.value == EmptyList &&
            topRatedList.value == EmptyList &&
            comingSoonList.value == EmptyList &&
            recentList.value == EmptyList &&
            mostHypedList.value == EmptyList
    }

    private suspend fun getListData(listType: ListType): Flow<ListData> {
        return gameRepository.getListDataForType(listType)
    }
}
