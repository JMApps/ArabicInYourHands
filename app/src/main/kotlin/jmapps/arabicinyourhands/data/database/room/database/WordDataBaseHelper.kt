package jmapps.arabicinyourhands.data.database.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategories
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategoriesDao
import jmapps.arabicinyourhands.data.database.room.model.words.WordItems
import jmapps.arabicinyourhands.data.database.room.model.words.WordItemsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [WordCategories::class, WordItems::class], version = 5, exportSchema = false)
abstract class WordDataBaseHelper : RoomDatabase() {

    abstract fun wordCategoriesDao(): WordCategoriesDao
    abstract fun wordItemsDao(): WordItemsDao

    companion object {

        @Volatile
        private var INSTANCE: WordDataBaseHelper? = null
        fun getDatabase(context: Context, scope: CoroutineScope): WordDataBaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDataBaseHelper::class.java,
                    "WordsDatabase"
                )
                    .addCallback(WordsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordsDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                INSTANCE.let { database ->
                    scope.launch(Dispatchers.IO) {
                        database?.wordCategoriesDao()?.let { populateWordCategories(it) }
                        database?.wordItemsDao()?.let { populateWordItems(it) }
                    }
                }
            }
        }

        fun populateWordCategories(wordCategoriesDao: WordCategoriesDao) {
            wordCategoriesDao.getWordCategoriesList("AddDateTime")
        }

        fun populateWordItems(wordItemsDao: WordItemsDao) {
            wordItemsDao.getAllWordsList(0, "AddDateTime")
        }
    }
}