package jmapps.arabicinyourhands.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.room.model.words.WordItems
import jmapps.arabicinyourhands.ui.holder.WordItemsHolder

class WordItemsAdapter(
    private val context: Context,
    private var wordItemList: MutableList<WordItems>,
    private val onLongWordItemClick: OnLongWordItemClick) : RecyclerView.Adapter<WordItemsHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var firstWordItemList: MutableList<WordItems>? = null

    init {
        this.firstWordItemList = wordItemList
    }

    interface OnLongWordItemClick {
        fun itemClickRenameItem(wordItemId: Long, word: String, wordTranscription: String, wordTranslate: String)

        fun itemClickDeleteItem(wordItemId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemsHolder {
        val viewItem = inflater.inflate(R.layout.item_word, parent, false)
        return (WordItemsHolder(viewItem))
    }

    override fun onBindViewHolder(holder: WordItemsHolder, position: Int) {
        val current = wordItemList[position]

        holder.tvWordColor.setBackgroundColor(Color.parseColor(current.wordItemColor))

        holder.tvWord.text = current.word
        holder.wordTranscriptionState(current.wordTranscription)
        holder.tvWordTranslate.text = current.wordTranslate

        // Пока оставить
        val wordAddDateTime = context.getString(R.string.action_add_time_item_word, "\n${current.addDateTime}")
        val wordChangeDateTime = context.getString(R.string.action_change_time_item_word, "\n${current.changeDateTime}")

        holder.findOnLongWordItemClick(onLongWordItemClick, current._id, current.word, current.wordTranscription, current.wordTranslate)
    }

    override fun getItemCount() = wordItemList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                wordItemList = if (charString.isEmpty()) {
                    firstWordItemList as MutableList<WordItems>
                } else {
                    val filteredList = ArrayList<WordItems>()
                    for (row in firstWordItemList!!) {
                        if (row._id.toString().contains(charSequence) ||
                            row.word.toLowerCase().contains(charString.toLowerCase()) ||
                            row.wordTranscription.toLowerCase().contains(charString.toLowerCase()) ||
                            row.wordTranslate.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = wordItemList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                wordItemList = filterResults.values as ArrayList<WordItems>
                notifyDataSetChanged()
            }
        }
    }
}