package jmapps.arabicinyourhands.data.database.helper

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private const val dbVersion = 1

class DBOpenHelper(private val context: Context) :
    SQLiteAssetHelper(context, "ArabicInDB", null, dbVersion) {

    init {
        setForcedUpgrade(dbVersion)
    }
}