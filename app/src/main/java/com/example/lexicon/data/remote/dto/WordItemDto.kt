package com.example.lexicon.data.remote.dto

data class WordItemDto(
    val meanings: List<MeaningDto>,
    val phonetic: String? = null,
    val word: String? = null
)