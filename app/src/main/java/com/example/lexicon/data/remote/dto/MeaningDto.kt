package com.example.lexicon.data.remote.dto

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
)