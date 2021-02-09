package jmapps.arabicinyourhands.ui.repository

import androidx.lifecycle.LiveData
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategories
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategoriesDao

class WordCategoriesRepository(private val wordCategoriesDao: WordCategoriesDao) {
    fun allWordCategories(orderBy: String): LiveData<MutableList<WordCategories>> {
        return wordCategoriesDao.getWordCategoriesList(orderBy)
    }

    suspend fun insertWordCategory(wordCategories: WordCategories) {
        wordCategoriesDao.insertWordCategory(wordCategories)
    }

    suspend fun updateWordCategory(newTitle: String, newColor: String, newDateTime: String, newPriority: Long, wordCategoryId: Long) {
        wordCategoriesDao.updateWordCategory(newTitle, newColor, newDateTime, newPriority, wordCategoryId)
    }

    suspend fun updateWordItemColor(newColor: String, wordCategoryId: Long) {
        wordCategoriesDao.updateWordItemColor(newColor, wordCategoryId)
    }

    suspend fun deleteWordCategory(wordCategoryId: Long) {
        wordCategoriesDao.deleteWordCategory(wordCategoryId)
    }

    suspend fun deleteWordItem(wordCategoryId: Long) {
        wordCategoriesDao.deleteWordItem(wordCategoryId)
    }

    suspend fun deleteAllWordCategories() {
        wordCategoriesDao.deleteAllWordCategories()
    }

    suspend fun deleteAllWordItems() {
        wordCategoriesDao.deleteAllWordItems()
    }
}