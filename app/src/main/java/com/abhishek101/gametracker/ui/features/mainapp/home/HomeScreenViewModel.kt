package com.abhishek101.gametracker.ui.features.mainapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek101.core.models.GameList
import com.abhishek101.core.repositories.GameRepository
import com.abhishek101.core.repositories.ListType
import com.abhishek101.core.repositories.ListType.COMING_SOON
import com.abhishek101.core.repositories.ListType.MOST_HYPED
import com.abhishek101.core.repositories.ListType.RECENT
import com.abhishek101.core.repositories.ListType.SHOWCASE
import com.abhishek101.core.repositories.ListType.TOP_RATED
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val gameRepository: GameRepository) : ViewModel() {
    val showcaseList = mutableStateOf<GameList?>(null)
    val topRatedList = mutableStateOf<GameList?>(null)
    val comingSoonList = mutableStateOf<GameList?>(null)
    val recentList = mutableStateOf<GameList?>(null)
    val mostHypedList = mutableStateOf<GameList?>(null)

    init {
        getListData(SHOWCASE)
        getListData(COMING_SOON)
        getListData(TOP_RATED)
        getListData(RECENT)
        getListData(MOST_HYPED)
    }

    private fun getListData(listType: ListType) {
        viewModelScope.launch {
            gameRepository.getListDataForType(listType).collect {
                when (listType) {
                    SHOWCASE -> showcaseList.value = it
                    TOP_RATED -> topRatedList.value = it
                    COMING_SOON -> comingSoonList.value = it
                    RECENT -> recentList.value = it
                    MOST_HYPED -> mostHypedList.value = it
                }
            }
        }
    }
}
