package com.example.lexicon.domain.repository

import com.example.lexicon.domain.model.WordItem
import kotlinx.coroutines.flow.Flow
import com.example.lexicon.util.Result

interface DictionaryRepository {
    suspend fun getWordResult(
        word: String
    ): Flow<Result<WordItem>>
}