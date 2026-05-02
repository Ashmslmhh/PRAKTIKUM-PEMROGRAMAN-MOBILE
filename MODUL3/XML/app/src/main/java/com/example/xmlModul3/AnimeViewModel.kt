package com.example.xmlModul3

import androidx.lifecycle.ViewModel
import com.example.animexml.model.Anime
import com.example.xmlModul3.data.animeList as animeDataList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnimeViewModel : ViewModel() {
    private val _animeList = MutableStateFlow(animeDataList)
    val animeList: StateFlow<List<Anime>> = _animeList
}