package jmapps.arabicinyourhands.data.database.room.model.words

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Table_of_words")
data class WordItems(
    @PrimaryKey(autoGenerate = true)
    val _id: Long,
    val displayBy: Long,
    val word: String,
    val wordTranscription: String,
    val wordTranslate: String,
    val wordItemColor: String,
    val addDateTime: String,
    val changeDateTime: String,
    val priority: Long
)
