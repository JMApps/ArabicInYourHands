package jmapps.arabicinyourhands.data.database.content

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.arabicinyourhands.data.database.helper.DBOpenHelper
import jmapps.arabicinyourhands.ui.model.ContentModel

class ContentLists(private val context: Context?) {

    private lateinit var database: SQLiteDatabase

    fun getFirstVolumeContents(displayBy: Int): MutableList<ContentModel> {

        database = DBOpenHelper(context!!).readableDatabase

        val cursor: Cursor = database.query(
            "Table_of_first_contents",
            null,
            "DisplayBy = $displayBy",
            null,
            null,
            null,
            null
        )

        val firstContents = ArrayList<ContentModel>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val contents = ContentModel(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("ArabicName")),
                    cursor.getString(cursor.getColumnIndex("ArabicContent")),
                    cursor.getString(cursor.getColumnIndex("TranslationName")),
                    cursor.getString(cursor.getColumnIndex("TranslationContent")),
                    cursor.getString(cursor.getColumnIndex("AudioName"))
                )
                firstContents.add(contents)
                cursor.moveToNext()
                if (cursor.isClosed) {
                    cursor.close()
                }
            }
        }
        return firstContents
    }

    fun getSecondVolumeContents(displayBy: Int): MutableList<ContentModel> {

        database = DBOpenHelper(context!!).readableDatabase

        val cursor: Cursor = database.query(
            "Table_of_second_contents",
            null,
            "DisplayBy = $displayBy",
            null,
            null,
            null,
            null
        )

        val secondContents = ArrayList<ContentModel>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val contents = ContentModel(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("ArabicName")),
                    cursor.getString(cursor.getColumnIndex("ArabicContent")),
                    cursor.getString(cursor.getColumnIndex("TranslationName")),
                    cursor.getString(cursor.getColumnIndex("TranslationContent")),
                    cursor.getString(cursor.getColumnIndex("AudioName"))
                )
                secondContents.add(contents)
                cursor.moveToNext()
                if (cursor.isClosed) {
                    cursor.close()
                }
            }
        }
        return secondContents
    }
}