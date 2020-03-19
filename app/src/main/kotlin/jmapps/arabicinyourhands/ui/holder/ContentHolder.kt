package jmapps.arabicinyourhands.ui.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val llContentItem: LinearLayout = itemView.findViewById(R.id.llContentItem)
    val tvArabicName: TextView = itemView.findViewById(R.id.tvArabicName)
    val tvArabicContent: TextView = itemView.findViewById(R.id.tvArabicContent)
    val tvTranslationName: TextView = itemView.findViewById(R.id.tvTranslationName)
    val tvTranslationContent: TextView = itemView.findViewById(R.id.tvTranslationContent)

    fun findItemClick(onContentItemClick: ContentAdapter.OnContentItemClick, contentId: Int, contentPosition: Int) {
        itemView.setOnClickListener {
            onContentItemClick.onItemClick(contentId, contentPosition)
        }
    }
}