package jmapps.arabicinyourhands.ui.repository

import androidx.lifecycle.LiveData
import jmapps.arabicinyourhands.data.database.room.model.words.WordItems
import jmapps.arabicinyourhands.data.database.room.model.words.WordItemsDao

class WordItemsRepository(private val wordItemsDao: WordItemsDao) {
    fun getAllWordItems(displayBy: Long, orderBy: String): LiveData<MutableList<WordItems>> {
        return wordItemsDao.getAllWordsList(displayBy, orderBy)
    }

    suspend fun insertWordItem(wordItems: WordItems) {
        wordItemsDao.insertWordItem(wordItems)
    }

    suspend fun updateWordItem(newWord: String, newWordTranscription: String, newWordTranslate: String, newDisplayBy: Long, newDateTime: String, wordId: Long) {
        wordItemsDao.updateWordItem(newWord, newWordTranscription, newWordTranslate, newDisplayBy, newDateTime, wordId)
    }

    suspend fun deleteWordItem(wordId: Long) {
        wordItemsDao.deleteWordItem(wordId)
    }

    suspend fun deleteAllWordFromCategory(wordCategoryId: Long) {
        wordItemsDao.deleteAllWordFromCategory(wordCategoryId)
    }
}