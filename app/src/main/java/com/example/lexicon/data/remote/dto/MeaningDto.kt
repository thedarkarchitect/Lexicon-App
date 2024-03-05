package com.example.lexicon.data.remote.dto

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String? = null,
)