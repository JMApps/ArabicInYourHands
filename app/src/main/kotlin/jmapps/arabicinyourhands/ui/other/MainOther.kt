package jmapps.arabicinyourhands.ui.other

import java.text.SimpleDateFormat
import java.util.*

class MainOther {
    private val date = Calendar.getInstance().time
    val currentTime = date.toString("dd/MM/yyyy HH:mm:ss")

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}