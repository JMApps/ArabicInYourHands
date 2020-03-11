package jmapps.arabicinyourhands.data.database.lists

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.arabicinyourhands.data.database.helper.DBOpenHelper
import jmapps.arabicinyourhands.ui.model.ChapterModel

class ChapterLists(private val context: Context?) {

    private lateinit var database: SQLiteDatabase

    val getFirstChapters: MutableList<ChapterModel>
        get() {
            database = DBOpenHelper(context!!).readableDatabase

            val cursor: Cursor = database.query(
                "Table_of_first_chapters",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val firstChapters = ArrayList<ChapterModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val chapters = ChapterModel(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("ChapterIcon")),
                        cursor.getString(cursor.getColumnIndex("ChapterTitle")),
                        cursor.getString(cursor.getColumnIndex("ChapterTitleArabic"))
                    )
                    firstChapters.add(chapters)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return firstChapters
        }

    val getSecondChapters: MutableList<ChapterModel>
        get() {
            database = DBOpenHelper(context!!).readableDatabase

            val cursor: Cursor = database.query(
                "Table_of_second_chapters",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val secondChapters = ArrayList<ChapterModel>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val chapters = ChapterModel(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("ChapterIcon")),
                        cursor.getString(cursor.getColumnIndex("ChapterTitle")),
                        cursor.getString(cursor.getColumnIndex("ChapterTitleArabic"))
                    )
                    secondChapters.add(chapters)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return secondChapters
        }
}