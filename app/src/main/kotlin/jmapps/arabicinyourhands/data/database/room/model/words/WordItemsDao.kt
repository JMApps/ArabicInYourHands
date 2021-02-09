package jmapps.arabicinyourhands.data.database.room.model.words

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordItemsDao {
    @Query("SELECT * FROM Table_of_words WHERE displayBy = :wordCategoryId ORDER BY CASE :orderBy WHEN 'addDateTime' THEN addDateTime WHEN 'changeDateTime' THEN changeDateTime WHEN 'alphabet' THEN word END ASC")
    fun getAllWordsList(wordCategoryId: Long, orderBy: String): LiveData<MutableList<WordItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordItem(wordItems: WordItems?)

    @Query("UPDATE Table_of_words SET word = :newWord, wordTranscription = :newWordTranscription, wordTranslate = :newWordTranslate, displayBy = :newDisplayBy, changeDateTime = :newDateTime WHERE _id = :wordId")
    suspend fun updateWordItem(newWord: String, newWordTranscription: String, newWordTranslate: String, newDisplayBy: Long, newDateTime: String, wordId: Long)

    @Query("DELETE FROM Table_of_words WHERE _id = :wordId")
    suspend fun deleteWordItem(wordId: Long)

    @Query("DELETE FROM Table_of_words WHERE displayBy = :wordCategoryId")
    suspend fun deleteAllWordFromCategory(wordCategoryId: Long)
}