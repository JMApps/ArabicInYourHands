package jmapps.arabicinyourhands.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategories

class ChangeWordCategoryAdapter(
    context: Context,
    private val wordCategoryList: MutableList<WordCategories>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_change_word_category, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        val current = wordCategoryList[position]

        vh.categoryId.text = (position + 1).toString()
        vh.categoryTitle.text = current.wordCategoryTitle

        return view

    }

    override fun getCount() = wordCategoryList.size

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int) = wordCategoryList[position]._id

    private inner class ItemRowHolder(row: View?) {
        val categoryId: TextView = row?.findViewById(R.id.text_change_word_category_id) as TextView
        val categoryTitle: TextView = row?.findViewById(R.id.text_change_word_category_title) as TextView
    }
}