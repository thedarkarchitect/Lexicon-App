package com.example.lexicon.data.repository

import android.app.Application
import com.example.lexicon.R
import com.example.lexicon.data.mapper.toWordItem
import com.example.lexicon.data.remote.api.DictionaryApi
import com.example.lexicon.domain.model.WordItem
import com.example.lexicon.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.lexicon.util.Result
import retrofit2.HttpException
import java.io.IOException

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val application: Application//this will be used to tap into the context of the application
): DictionaryRepository {
    override suspend fun getWordResult(word: String): Flow<Result<WordItem>>  = flow {
        emit(Result.Loading(true))
        val remoteWordResult = try {
            dictionaryApi.getWordResult(word)
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Result.Error(application.getString(R.string.can_t_get_result)))//this is because the class is not a composable
            emit(Result.Loading(false))
            return@flow
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Result.Error(application.getString(R.string.can_t_get_result)))//this is because the class is not a composable
            emit(Result.Loading(false))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(application.getString(R.string.can_t_get_result)))//this is because the class is not a composable
            emit(Result.Loading(false))
            return@flow
        }

        remoteWordResult?.let { wordResultDto ->
            wordResultDto[0]?.let{ wordItem ->
                emit(Result.Success(
                    wordItem.toWordItem()
                ))
                emit(Result.Loading(false))
                return@flow
            }
        }

        emit(Result.Loading(false))
    }
}