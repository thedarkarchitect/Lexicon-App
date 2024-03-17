package com.example.lexicon.data.mapper

import com.example.lexicon.data.remote.dto.DefinitionDto
import com.example.lexicon.data.remote.dto.MeaningDto
import com.example.lexicon.data.remote.dto.WordItemDto
import com.example.lexicon.domain.model.Definition
import com.example.lexicon.domain.model.Meaning
import com.example.lexicon.domain.model.WordItem

fun WordItemDto.toWordItem() = WordItem (
        word = word ?: "",
        meanings = meanings?.map {
            it.toMeaning()
        } ?: emptyList(),
        phonetic = phonetic ?: ""
)

fun MeaningDto.toMeaning() = Meaning(
    definition = definitionDtoToDefinition(definitions?.get(0)),
    partOfSpeech = partOfSpeech ?: ""
)

fun definitionDtoToDefinition(
    definitionDto: DefinitionDto?
) = Definition(
    definition = definitionDto?.definition ?: "",
    example = definitionDto?.example ?: ""
)