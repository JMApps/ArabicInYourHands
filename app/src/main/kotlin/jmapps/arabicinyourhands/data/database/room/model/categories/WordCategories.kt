package jmapps.arabicinyourhands.data.database.room.model.categories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Table_of_word_categories")
data class WordCategories(
    @PrimaryKey(autoGenerate = true)
    val _id: Long,
    val wordCategoryTitle: String,
    val wordCategoryColor: String,
    val priority: Long,
    val addDateTime: String,
    val changeDateTime: String
)