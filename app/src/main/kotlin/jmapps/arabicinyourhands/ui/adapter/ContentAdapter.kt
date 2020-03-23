package jmapps.arabicinyourhands.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.holder.ContentHolder
import jmapps.arabicinyourhands.ui.model.ContentModel

class ContentAdapter(
    private val context: Context?,
    private val contentList: MutableList<ContentModel>,
    private val onContentItemClick: OnContentItemClick) : RecyclerView.Adapter<ContentHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var currentIndex = -1

    interface OnContentItemClick {
        fun onItemClick(contentId: Int, contentPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val contentItem = inflater.inflate(R.layout.item_content, parent, false)
        return ContentHolder(contentItem)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val current = contentList[position]

        if (currentIndex == position) {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.lightColorId))
        } else {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.reMain))
        }

        if (!current.ArabicName.isNullOrEmpty()) {
            holder.tvArabicName.visibility = View.VISIBLE
            holder.tvArabicName.text = current.ArabicName
        } else {
            holder.tvArabicName.visibility = View.GONE
        }

        if (!current.TranslationName.isNullOrEmpty()) {
            holder.tvTranslationName.visibility = View.VISIBLE
            holder.tvTranslationName.text = current.TranslationName
        } else {
            holder.tvTranslationName.visibility = View.GONE
        }

        holder.tvArabicContent.text = current.ArabicContent
        holder.tvTranslationContent.text = current.TranslationContent

        holder.findItemClick(onContentItemClick, current.contentId, position)
    }

    fun itemSelected(currentIndex: Int) {
        this.currentIndex = currentIndex
        notifyDataSetChanged()
    }
}