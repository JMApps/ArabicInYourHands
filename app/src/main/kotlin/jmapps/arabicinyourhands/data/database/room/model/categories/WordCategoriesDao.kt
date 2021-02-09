package jmapps.arabicinyourhands.data.database.room.model.categories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordCategoriesDao {
    @Query("SELECT * FROM Table_of_word_categories ORDER BY CASE :orderBy WHEN 'addDateTime' THEN addDateTime WHEN 'changeDateTime' THEN changeDateTime WHEN 'color' THEN wordCategoryColor WHEN 'alphabet' THEN wordCategoryTitle END ASC, CASE :orderBy WHEN 'priority' THEN priority END DESC")
    fun getWordCategoriesList(orderBy: String): LiveData<MutableList<WordCategories>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordCategory(wordCategories: WordCategories)

    @Query("UPDATE Table_of_word_categories SET wordCategoryTitle = :newTitle, wordCategoryColor = :newColor, changeDateTime = :newDateTime, priority = :newPriority WHERE _id = :wordCategoryId")
    suspend fun updateWordCategory(newTitle: String, newColor: String, newDateTime: String, newPriority: Long, wordCategoryId: Long)

    @Query("UPDATE Table_of_words SET wordItemColor = :newColor WHERE displayBy = :wordCategoryId")
    suspend fun updateWordItemColor(newColor: String, wordCategoryId: Long)

    @Query("DELETE FROM Table_of_word_categories WHERE _id = :wordCategoryId")
    suspend fun deleteWordCategory(wordCategoryId: Long)

    @Query("DELETE FROM Table_of_words WHERE displayBy = :wordCategoryId")
    suspend fun deleteWordItem(wordCategoryId: Long)

    @Query("DELETE FROM Table_of_word_categories")
    suspend fun deleteAllWordCategories()

    @Query("DELETE FROM Table_of_words")
    suspend fun deleteAllWordItems()
}