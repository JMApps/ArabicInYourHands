package jmapps.arabicinyourhands.data.database.lists

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.arabicinyourhands.data.database.helper.DBOpenHelper
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SubChapterLists(private val context: Context?) {

    private lateinit var database: SQLiteDatabase

    fun getFirstSubChapters(displayBy: Int): MutableList<SubChapterModel> {

        database = DBOpenHelper(context!!).readableDatabase

        val cursor: Cursor = database.query(
            "Table_of_first_sub_chapters",
            null,
            "DisplayBy = $displayBy",
            null,
            null,
            null,
            null
        )

        val firstSubChapters = ArrayList<SubChapterModel>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val subChapters = SubChapterModel(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("DialogPicture")),
                    cursor.getString(cursor.getColumnIndex("Dialog")),
                    cursor.getString(cursor.getColumnIndex("DialogTitle"))
                )
                firstSubChapters.add(subChapters)
                cursor.moveToNext()
                if (cursor.isClosed) {
                    cursor.close()
                }
            }
        }
        return firstSubChapters
    }

    fun getSecondSubChapters(displayBy: Int): MutableList<SubChapterModel> {

        database = DBOpenHelper(context!!).readableDatabase

        val cursor: Cursor = database.query(
            "Table_of_second_sub_chapters",
            null,
            "DisplayBy = $displayBy",
            null,
            null,
            null,
            null
        )

        val secondSubChapters = ArrayList<SubChapterModel>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val subChapters = SubChapterModel(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("DialogPicture")),
                    cursor.getString(cursor.getColumnIndex("Dialog")),
                    cursor.getString(cursor.getColumnIndex("DialogTitle"))
                )
                secondSubChapters.add(subChapters)
                cursor.moveToNext()
                if (cursor.isClosed) {
                    cursor.close()
                }
            }
        }
        return secondSubChapters
    }
}