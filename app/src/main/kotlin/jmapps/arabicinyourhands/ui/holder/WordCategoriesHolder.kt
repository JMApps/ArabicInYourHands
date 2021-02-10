package jmapps.arabicinyourhands.ui.holder

import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.PopupMenu
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.adapter.WordCategoriesAdapter
import jmapps.arabicinyourhands.ui.fragment.ToolsWordCategoryBottomSheet.Companion.ARG_WORD_CATEGORY_ADD_DATE_TIME
import jmapps.arabicinyourhands.ui.fragment.ToolsWordCategoryBottomSheet.Companion.ARG_WORD_CATEGORY_CHANGE_DATE_TIME
import jmapps.arabicinyourhands.ui.preferences.SharedLocalProperties

class WordCategoriesHolder(viewCategory: View) : RecyclerView.ViewHolder(viewCategory),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
    private var sharedLocalPreferences: SharedLocalProperties = SharedLocalProperties(preferences)

    val llWordCategoryItemPriority: LinearLayoutCompat = viewCategory.findViewById(R.id.layout_word_category_item_priority)
    val tvWordCategoryColor: TextView = viewCategory.findViewById(R.id.text_word_category_color)
    val tvWordCategoryTitle: TextView = viewCategory.findViewById(R.id.text_word_category_title)
    val tvWordCategoryAddDateTime: TextView = viewCategory.findViewById(R.id.text_view_word_category_item_add_date_time)
    val tvWordCategoryChangeDateTime: TextView = viewCategory.findViewById(R.id.text_view_word_category_item_change_date_time)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)
        setShowAddChangeDateTime()
    }

    fun findItemClick(
        onItemClickWordCategory: WordCategoriesAdapter.OnItemClickWordCategory,
        wordCategoryId: Long,
        wordCategoryPosition: Int,
        wordCategoryTitle: String,
        wordCategoryColor: String,
        wordCategoryPriority: Long) {
        itemView.setOnClickListener {
            onItemClickWordCategory.onItemClickWordCategory(wordCategoryId, wordCategoryPosition, wordCategoryTitle, wordCategoryColor, wordCategoryPriority)
        }
    }

    fun findLongItemClick(
        onLongClickWordCategory: WordCategoriesAdapter.OnLongClickWordCategory,
        wordCategoryId: Long,
        wordCategoryTitle: String,
        wordCategoryColor: String,
        wordCategoryPriority: Long) {
        itemView.setOnLongClickListener {
            val pop = PopupMenu(itemView.context, tvWordCategoryTitle)
            pop.inflate(R.menu.menu_change_category_popup)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_change_item -> {
                        onLongClickWordCategory.itemClickRenameCategory(wordCategoryId, wordCategoryTitle, wordCategoryColor, wordCategoryPriority)
                    }
                    R.id.popup_delete_item -> {
                        onLongClickWordCategory.itemClickDeleteCategory(wordCategoryId, wordCategoryTitle)
                    }
                }
                true
            }
            pop.show()
            true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setShowAddChangeDateTime()
    }

    private fun setShowAddChangeDateTime() {
        val addShowingDateTime = sharedLocalPreferences.getBooleanValue(ARG_WORD_CATEGORY_ADD_DATE_TIME, false)
        val changeShowingDateTime = sharedLocalPreferences.getBooleanValue(ARG_WORD_CATEGORY_CHANGE_DATE_TIME, false)

        if (!addShowingDateTime) {
            tvWordCategoryAddDateTime.visibility = View.GONE
        } else {
            tvWordCategoryAddDateTime.visibility = View.VISIBLE
        }

        if (!changeShowingDateTime) {
            tvWordCategoryChangeDateTime.visibility = View.GONE
        } else {
            tvWordCategoryChangeDateTime.visibility = View.VISIBLE
        }
    }
}