package jmapps.arabicinyourhands.ui.holder

import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.file.TypeFace
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.fragment.ToolsBottomSheet
import jmapps.arabicinyourhands.ui.fragment.ToolsBottomSheet.Companion.AlignmentLeft

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    val llContentItem: LinearLayoutCompat = itemView.findViewById(R.id.llContentItem)
    val tvArabicName: TextView = itemView.findViewById(R.id.tvArabicName)
    val tvArabicContent: TextView = itemView.findViewById(R.id.tvArabicContent)
    val tvTranslationName: TextView = itemView.findViewById(R.id.tvTranslationName)
    val tvTranslationContent: TextView = itemView.findViewById(R.id.tvTranslationContent)
    private val btnShareItem: Button = itemView.findViewById(R.id.btnShareItem)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context).registerOnSharedPreferenceChangeListener(this)
        textSizes()
        textArabicFont()
        textTranslationFont()
        textsShowState()
        textAlignment()
    }

    fun findItemClick(onContentItemClick: ContentAdapter.OnContentItemClick, contentId: Int, contentPosition: Int) {
        itemView.setOnClickListener {
            onContentItemClick.onItemClick(contentId, contentPosition)
        }
    }

    fun findItemShareClick(onShareItemClick: ContentAdapter.OnShareItemClick, content: String) {
        btnShareItem.setOnClickListener {
            onShareItemClick.shareItemClick(content)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        textSizes()
        textArabicFont()
        textTranslationFont()
        textsShowState()
        textAlignment()
    }

    private fun textSizes() {
        val arabicProgress = preferences.getInt(ToolsBottomSheet.ArabicTextSize, 1)
        val translationProgress = preferences.getInt(ToolsBottomSheet.TranslationTextSize, 1)

        tvArabicName.textSize = textSizeValues[arabicProgress].toFloat()
        tvArabicContent.textSize = textSizeValues[arabicProgress].toFloat()

        tvTranslationName.textSize = textSizeValues[translationProgress].toFloat()
        tvTranslationContent.textSize = textSizeValues[translationProgress].toFloat()
    }

    private fun textArabicFont() {
        val arabicFontOne = preferences.getBoolean(ToolsBottomSheet.ArabicFontOne, true)
        val arabicFontTwo = preferences.getBoolean(ToolsBottomSheet.ArabicFontTwo, false)
        val arabicFontThree = preferences.getBoolean(ToolsBottomSheet.ArabicFontThree, false)

        when(true) {

            arabicFontOne -> {
                arabicFont("fonts/droid_naskh.ttf")
            }

            arabicFontTwo -> {
                arabicFont("fonts/quran_font.ttf")
            }

            arabicFontThree -> {
                arabicFont("fonts/uthmanic.ttf")
            }
        }
    }

    private fun arabicFont(font: String) {
        tvArabicName.typeface = TypeFace()[itemView.context!!, font]
        tvArabicContent.typeface = TypeFace()[itemView.context!!, font]
    }

    private fun textTranslationFont() {
        val translationFontOne = preferences.getBoolean(ToolsBottomSheet.TranslationFontOne, true)
        val translationFontTwo = preferences.getBoolean(ToolsBottomSheet.TranslationFontTwo, false)
        val translationFontThree = preferences.getBoolean(ToolsBottomSheet.TranslationFontThree, false)

        when(true) {

            translationFontOne -> {
                translationFont("fonts/gilroy.ttf")
            }

            translationFontTwo -> {
                translationFont("fonts/cambria_medium.ttf")
            }

            translationFontThree -> {
                translationFont("fonts/serif.ttf")
            }
        }
    }

    private fun translationFont(font: String) {
        tvTranslationName.typeface = TypeFace()[itemView.context!!, font]
        tvTranslationContent.typeface = TypeFace()[itemView.context!!, font]
    }

    private fun textAlignment() {
        val alignmentLeft = preferences.getBoolean(AlignmentLeft, false)
        val alignmentCenter = preferences.getBoolean(ToolsBottomSheet.AlignmentCenter, true)
        val alignmentRight = preferences.getBoolean(ToolsBottomSheet.AlignmentRight, false)

        when (true) {

            alignmentLeft -> {
                tvArabicName.gravity = Gravity.END
                tvArabicContent.gravity = Gravity.END
                tvTranslationName.gravity = Gravity.START
                tvTranslationContent.gravity = Gravity.START
            }

            alignmentCenter -> {
                tvArabicName.gravity = Gravity.CENTER
                tvArabicContent.gravity = Gravity.CENTER
                tvTranslationName.gravity = Gravity.CENTER
                tvTranslationContent.gravity = Gravity.CENTER
            }

            alignmentRight -> {
                tvArabicName.gravity = Gravity.START
                tvArabicContent.gravity = Gravity.START
                tvTranslationName.gravity = Gravity.END
                tvTranslationContent.gravity = Gravity.END
            }
        }
    }

    private fun textsShowState() {
        val switchArabicState = preferences.getBoolean(ToolsBottomSheet.SwitchArabicShow, true)
        val switchTranslationState = preferences.getBoolean(ToolsBottomSheet.SwitchTranslationShow, true)
        val switchShareButtonState = preferences.getBoolean(ToolsBottomSheet.SwitchShareButtonShow, true)

        if (switchArabicState) {
            tvArabicName.visibility = View.VISIBLE
            tvArabicContent.visibility = View.VISIBLE
        } else {
            tvArabicName.visibility = View.INVISIBLE
            tvArabicContent.visibility = View.INVISIBLE
        }

        if (switchTranslationState) {
            tvTranslationName.visibility = View.VISIBLE
            tvTranslationContent.visibility = View.VISIBLE
        } else {
            tvTranslationName.visibility = View.INVISIBLE
            tvTranslationContent.visibility = View.INVISIBLE
        }

        if (switchShareButtonState) {
            btnShareItem.visibility = View.VISIBLE
        } else {
            btnShareItem.visibility = View.GONE
        }
    }
}