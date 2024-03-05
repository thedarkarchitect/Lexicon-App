package com.example.lexicon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexicon.domain.repository.DictionaryRepository
import com.example.lexicon.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var searchJob: Job? = null

    init {

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            loadWordResult()
        }
    }

    fun onEvents(events: MainUiEvents) {
        when(events){
            MainUiEvents.OnSearchClick -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch{
                    loadWordResult()
                }
            }
            is MainUiEvents.OnSearchWordChange -> {
                _mainState.update {
                    it.copy(
                        searchWord = events.newWord.lowercase()
                    )
                }
            }
        }
    }

    private fun loadWordResult() {
        viewModelScope.launch {
            repository.getWordResult(
                mainState.value.searchWord//The word to get from api is got from the state of word
            ).collect { result -> //result from Result<WordItem>
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Result.Success -> {
                        result.data?.let {  wordItem ->
                            _mainState.update {
                                it.copy(
                                    wordItem = wordItem
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}