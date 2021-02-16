package jmapps.arabicinyourhands.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategories
import jmapps.arabicinyourhands.ui.holder.WordCategoriesHolder

class WordCategoriesAdapter(
    private val context: Context,
    private var wordCategoryList: MutableList<WordCategories>,
    private val onItemClickWordCategory: OnItemClickWordCategory,
    private val onLongClickWordCategory: OnLongClickWordCategory) : RecyclerView.Adapter<WordCategoriesHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var firstWordCategoryList: MutableList<WordCategories>? = null

    init {
        this.firstWordCategoryList = wordCategoryList
    }

    interface OnItemClickWordCategory {
        fun onItemClickWordCategory(
            wordCategoryId: Long,
            wordCategoryPosition: Int,
            wordCategoryTitle: String,
            wordCategoryColor: String,
            wordCategoryPriority: Long
        )
    }

    interface OnLongClickWordCategory {
        fun itemClickRenameCategory(
            wordCategoryId: Long,
            wordCategoryTitle: String,
            wordCategoryColor: String,
            wordCategoryPriority: Long
        )

        fun itemClickDeleteCategory(wordCategoryId: Long, wordCategoryTitle: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordCategoriesHolder {
        val viewCategory = inflater.inflate(R.layout.item_word_category, parent, false)
        return (WordCategoriesHolder(viewCategory))
    }

    override fun onBindViewHolder(holder: WordCategoriesHolder, position: Int) {
        val current = wordCategoryList[position]

        DrawableCompat.setTint(holder.tvWordCategoryColor.background, Color.parseColor(current.wordCategoryColor))
        holder.tvWordCategoryColor.text = (position + 1).toString()
        holder.tvWordCategoryTitle.text = current.wordCategoryTitle

        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                val priorityName = arrayListOf("#FFFFFF", "#FFF4CB", "#CBFFCF", "#FFCBDA")
                holder.llWordCategoryItemPriority.setBackgroundColor(Color.parseColor(priorityName[current.priority.toInt()]))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                val priorityName = arrayListOf("#263238", "#4092861A", "#40217D28", "#40FB6818")
                holder.llWordCategoryItemPriority.setBackgroundColor(Color.parseColor(priorityName[current.priority.toInt()]))
            }
        }

        val wordCategoryAddDateTime = context.getString(R.string.action_add_time_item_word, "\n${current.addDateTime}")
        val wordCategoryChangeDateTime = context.getString(R.string.action_change_time_item_word, "\n${current.changeDateTime}")

        holder.tvWordCategoryAddDateTime.text = wordCategoryAddDateTime
        holder.tvWordCategoryChangeDateTime.text = wordCategoryChangeDateTime

        holder.findItemClick(
            onItemClickWordCategory,
            current._id,
            position,
            current.wordCategoryTitle,
            current.wordCategoryColor,
            current.priority
        )
        holder.findLongItemClick(
            onLongClickWordCategory,
            current._id,
            current.wordCategoryTitle,
            current.wordCategoryColor,
            current.priority
        )
    }

    override fun getItemCount() = wordCategoryList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                wordCategoryList = if (charString.isEmpty()) {
                    firstWordCategoryList as MutableList<WordCategories>
                } else {
                    val filteredList = ArrayList<WordCategories>()
                    for (row in firstWordCategoryList!!) {
                        if (row._id.toString().contains(charSequence) ||
                            row.wordCategoryTitle.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = wordCategoryList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                wordCategoryList = filterResults.values as ArrayList<WordCategories>
                notifyDataSetChanged()
            }
        }
    }
}