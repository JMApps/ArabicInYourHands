package jmapps.arabicinyourhands.data.database.helper

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private const val dbVersion = 2

class DBOpenHelper(private val context: Context) :
    SQLiteAssetHelper(context, "ArabicInDB", null, dbVersion) {

    init {
        setForcedUpgrade()
    }
}