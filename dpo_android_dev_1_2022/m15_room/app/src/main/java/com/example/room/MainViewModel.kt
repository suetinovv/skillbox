package com.example.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {

    val fiveWords: StateFlow<List<Word>> = this.wordDao.getFiveWord()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun onButtonAdd(id: String) {
        viewModelScope.launch {
            val temp = wordDao.getWord(id)
            if (temp.isEmpty()) {
                wordDao.insert(
                    Word(
                        id = id,
                        count = 0
                    )
                )
            } else {
                wordDao.update(
                    Word(
                        id = id,
                        count = temp.first().count + 1
                    )
                )
            }
        }
    }

    fun onButtonClean() {
        viewModelScope.launch {
            wordDao.clear()
        }
    }

}