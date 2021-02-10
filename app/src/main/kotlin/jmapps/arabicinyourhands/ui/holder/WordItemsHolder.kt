package jmapps.arabicinyourhands.ui.holder

import android.content.Intent
import android.content.SharedPreferences
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.adapter.WordItemsAdapter
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORDS_TEXT_SIZE
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_CENTER_ALIGN
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_LEFT_ALIGN
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_RIGHT_ALIGN
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_STATE
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_TRANSCRIPTION_STATE
import jmapps.arabicinyourhands.ui.fragment.ToolsWordItemBottomSheet.Companion.ARG_WORD_TRANSLATE_STATE
import jmapps.arabicinyourhands.ui.preferences.SharedLocalProperties

class WordItemsHolder(wordView: View) : RecyclerView.ViewHolder(wordView),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
    private var sharedLocalPreferences: SharedLocalProperties = SharedLocalProperties(preferences)

    val tvWordColor: TextView = wordView.findViewById(R.id.text_word_color)
    val tvWord: TextView = wordView.findViewById(R.id.text_word)
    private val tvWordTranscription: TextView = wordView.findViewById(R.id.text_word_transcription)
    val tvWordTranslate: TextView = wordView.findViewById(R.id.text_word_translate)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)
        setTextSize()
        setShowWord()
        setAlignWord()
    }

    fun findOnLongWordItemClick(onLongWordItemClick: WordItemsAdapter.OnLongWordItemClick, wordItemId: Long, word: String, wordTranscription: String, wordTranslate: String) {
        itemView.setOnClickListener {
            val pop = PopupMenu(itemView.context, tvWord)
            pop.inflate(R.menu.menu_change_item_popup)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_change_item -> {
                        onLongWordItemClick.itemClickRenameItem(wordItemId, word, wordTranscription, wordTranslate)
                    }
                    R.id.popup_share_item -> {
                        if (wordTranscription.isNotEmpty()) {
                            shareWord("$word\n$wordTranscription\n$wordTranslate")
                        } else {
                            shareWord("$word\n$wordTranslate")
                        }
                    }
                    R.id.popup_delete_item -> {
                        onLongWordItemClick.itemClickDeleteItem(wordItemId)
                    }
                }
                true
            }
            pop.show()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setTextSize()
        setShowWord()
        setAlignWord()
    }

    private fun setTextSize() {
        val textSize = sharedLocalPreferences.getIntValue(ARG_WORDS_TEXT_SIZE, 18)
        tvWord.textSize = textSize.toFloat()
        tvWordTranscription.textSize = textSize.toFloat()
        tvWordTranslate.textSize = textSize.toFloat()
    }

    private fun setShowWord() {
        val wordState = sharedLocalPreferences.getBooleanValue(ARG_WORD_STATE, true)
        val wordTranslateState = sharedLocalPreferences.getBooleanValue(ARG_WORD_TRANSLATE_STATE, true)

        if (wordState) tvWord.visibility = View.VISIBLE else tvWord.visibility = View.GONE
        if (wordTranslateState) tvWordTranslate.visibility = View.VISIBLE else tvWordTranslate.visibility = View.GONE
    }

    private fun setAlignWord() {
        val leftAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_LEFT_ALIGN, true)
        val centerAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_CENTER_ALIGN, false)
        val rightAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_RIGHT_ALIGN, false)

        when (true) {
            leftAlignState -> {
                tvWord.gravity = Gravity.START
                tvWordTranscription.gravity = Gravity.START
                tvWordTranslate.gravity = Gravity.START
            }
            centerAlignState -> {
                tvWord.gravity = Gravity.CENTER
                tvWordTranscription.gravity = Gravity.CENTER
                tvWordTranslate.gravity = Gravity.CENTER
            }
            rightAlignState -> {
                tvWord.gravity = Gravity.END
                tvWordTranscription.gravity = Gravity.END
                tvWordTranslate.gravity = Gravity.END
            }
        }
    }

    fun wordTranscriptionState(strWordTranscription: String) {
        val wordTranscriptionState = sharedLocalPreferences.getBooleanValue(ARG_WORD_TRANSCRIPTION_STATE, true)
        if (strWordTranscription.isNotEmpty()) {
            if (wordTranscriptionState) tvWordTranscription.visibility = View.VISIBLE else tvWordTranscription.visibility = View.GONE
            tvWordTranscription.text = strWordTranscription
        } else {
            tvWordTranscription.visibility = View.GONE
        }
    }

    private fun shareWord(words: String) {
        val shareWord = Intent(Intent.ACTION_SEND)
        shareWord.type = "text/plain"
        shareWord.putExtra(Intent.EXTRA_TEXT, words)
        itemView.context?.startActivity(shareWord)
    }
}