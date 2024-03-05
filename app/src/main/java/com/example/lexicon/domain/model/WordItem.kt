package com.example.lexicon.domain.model

data class WordItem(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)