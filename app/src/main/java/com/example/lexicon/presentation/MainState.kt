package com.example.lexicon.presentation

import com.example.lexicon.domain.model.WordItem

data class MainState(
    val isLoading: Boolean = false,
    val searchWord: String = "",
    val wordItem: WordItem? = null
)
